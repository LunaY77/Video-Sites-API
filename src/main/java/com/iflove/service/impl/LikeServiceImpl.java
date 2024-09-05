package com.iflove.service.impl;

import com.iflove.entity.Const;
import com.iflove.entity.CountParams;
import com.iflove.entity.dto.Comment;
import com.iflove.entity.dto.LikesComment;
import com.iflove.entity.dto.LikesVideo;
import com.iflove.entity.dto.Videos;
import com.iflove.entity.vo.response.ListVO;
import com.iflove.entity.vo.response.VideoInfoVO;
import com.iflove.service.*;
import com.iflove.utils.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Resource
    RedisUtil redisUtil;
    @Resource
    VideosService videosService;
    @Resource
    LikesVideoService likesVideoService;
    @Resource
    LikesCommentService likesCommentService;
    @Resource
    CommentService commentService;

    /**
     * 点赞视频或评论
     * @param userId 用户
     * @param videoSid 视频
     * @param commentSid 评论
     * @param actionType 操作类型 （ 1 点赞 / 0 取消点赞）
     * @return 成功 null / 失败 错误信息
     */
    @Override
    public String action(String userId, String videoSid, String commentSid, Integer actionType) {
        if (actionType != 0 && actionType != 1) return "未知操作";
        if (videoSid.isBlank() == commentSid.isBlank()) return "未知点赞对象";
        Long videoId = null, commentId = null;
        try {
            videoId = videoSid.isBlank() ? null : Long.parseLong(videoSid);
            commentId = commentSid.isBlank() ? null : Long.parseLong(commentSid);
        } catch (NumberFormatException e) {
            return "id格式错误";
        }
        // 视频
        if (videoId != null) {
            // 判断是否已经点赞，避免重复操作
            int like = redisUtil.hGetLike(Const.VIDEO_USER_LIKED, userId, videoSid);
            if ((like ^ actionType) == 0) return "请勿重复操作";
            // 存储点赞信息
            redisUtil.hSaveLike(Const.VIDEO_USER_LIKED, userId, videoSid, actionType);
            redisUtil.zsIncr(Const.VIDEO_RECOMMEND_COUNT, videoSid, actionType == 0 ? -1 : 1);
            return null;
        }
        // 评论
        int like = redisUtil.hGetLike(Const.COMMENT_USER_LIKED, userId, commentSid);
        if ((like ^ actionType) == 0) return "请勿重复操作";
        // 存储点赞信息
        redisUtil.hSaveLike(Const.COMMENT_USER_LIKED, userId, commentSid, actionType);
        redisUtil.zsIncr(Const.COMMENT_RECOMMEND_COUNT, commentSid, actionType == 0 ? -1 : 1);
        return null;
    }

    /**
     * 返回用户的点赞列表
     * @param userId 用户
     * @param pageNum 页码
     * @param pageSize 大小
     * @return 结果集
     */
    @Override
    public ListVO<VideoInfoVO> likeList(String userId, int pageNum, int pageSize) {
        List<Long> videoIds = redisUtil.hGetLikeList(Const.VIDEO_USER_LIKED, userId);
        System.out.println(videoIds);
        // 获取分页初始值
        int start = pageNum * pageSize;
        // 分页查询
        List<VideoInfoVO> items = new ArrayList<>();
        for (int i = start; i < videoIds.size() && i < start + pageSize; i++) {
            long videoId = videoIds.get(i);
            items.add(
                    videosService
                            .query()
                            .eq("id", videoId)
                            .eq("is_deleted", 0)
                            .one()
                            .asViewObject(VideoInfoVO.class, v -> {
                                v.setClickCount(redisUtil.zsScore(Const.VIDEO_CLICK_COUNT, String.valueOf(videoId)));
                                v.setRecommendCount(redisUtil.zsScore(Const.VIDEO_RECOMMEND_COUNT, String.valueOf(videoId)));
                            })
            );
        }
        return new ListVO<>(items, (long) videoIds.size());
    }


    /**
     * 持久化存储 点赞关系
     */
    @Transactional
    @Override
    public void transLikeFromRedis2DB() {
        // 获得redis缓存的点赞关系
        // 处理 用户 - 视频 点赞关系 存储
        Map<Object, Object> videoUserLikedMap = redisUtil.hGetAllLikeList(Const.VIDEO_USER_LIKED);
        this.transLike(videoUserLikedMap,
                split -> new LikesVideo(Long.parseLong(split[0]), Long.parseLong(split[1])),
                likesVideoService::saveBatch);

        // 处理 用户 - 评论 点赞关系 存储
        Map<Object, Object> commentUserLikedMap = redisUtil.hGetAllLikeList(Const.COMMENT_USER_LIKED);
        this.transLike(commentUserLikedMap,
                split -> new LikesComment(Long.parseLong(split[0]), Long.parseLong(split[1])),
                likesCommentService::saveBatch);
    }

    private <T> void transLike(Map<Object, Object> likedMap, Function<String[], T> mapper, Consumer<List<T>> service) {
        List<T> likesList = new ArrayList<>();
        likedMap.forEach((k, v) -> {
            if (v.toString().equals("1")) {
                String[] split = k.toString().split("::");
                likesList.add(mapper.apply(split));
            }
        });
        service.accept(likesList);
    }

    /**
     * 持久化存储 点赞量，点击量
     */
    @Transactional
    @Override
    public void transCountValueFromRedis2DB() {
        // 处理 视频 点赞量
        Set<ZSetOperations.TypedTuple<String>> videoReCommendCountSet = redisUtil.zsRangeWithScores(Const.VIDEO_RECOMMEND_COUNT);
        transLikeCount(videoReCommendCountSet, Videos::insertRecommendCount, videosService::saveOrUpdateBatch);
        // 处理 视频 点击量
        Set<ZSetOperations.TypedTuple<String>> videoClickCountSet = redisUtil.zsRangeWithScores(Const.VIDEO_CLICK_COUNT);
        transLikeCount(videoClickCountSet, Videos::insertClickCount, videosService::saveOrUpdateBatch);
        // 处理 评论 点赞量
        Set<ZSetOperations.TypedTuple<String>> commendRecommendCountSet = redisUtil.zsRangeWithScores(Const.COMMENT_RECOMMEND_COUNT);
        transLikeCount(commendRecommendCountSet, Comment::insertCount, commentService::saveOrUpdateBatch);
    }

    private <T> void transLikeCount(Set<ZSetOperations.TypedTuple<String>> set, Function<CountParams, T> mapper, Consumer<List<T>> service) {
        List<T> lst = new ArrayList<>();
        set.forEach(s -> {
            String value = s.getValue();
            Double score = s.getScore();
            int count = score.intValue();
            long updatedAt = (long) ((score - count) * 1e13);
            Timestamp update = updatedAt == 0 ? null : new Timestamp(updatedAt);
            CountParams countParams = new CountParams(Long.parseLong(value), new Date(), count, update);
            T result = mapper.apply(countParams);
            lst.add(result);
        });
        service.accept(lst);
    }

    /**
     * 从 mysql 读取 点赞关系 到 redis
     */
    @Transactional
    @Override
    public void transLikeFromDB2Redis() {
        // 读取 用户 - 视频 点赞关系
        List<LikesVideo> likesVideoList = likesVideoService.list();
        likesVideoList.forEach(v -> {
            redisUtil.hSaveLike(Const.VIDEO_USER_LIKED, String.valueOf(v.getUserId()), String.valueOf(v.getVideoId()), 1);
        });
        // 读取结束，清空数据库
        likesVideoService.deleteAll();

        // 读取 用户 - 评论 点赞关系
        List<LikesComment> likesCommentList = likesCommentService.list();
        likesCommentList.forEach(v -> {
            redisUtil.hSaveLike(Const.COMMENT_USER_LIKED, String.valueOf(v.getUserId()), String.valueOf(v.getCommentId()), 1);
        });
        // 读取结束，清空数据库
        likesCommentService.deleteAll();
    }

    /**
     * 从 mysql 读取 点击量，点赞量 到 redis
     */
    @Transactional
    @Override
    public void transCountValueFromDB2Redis() {
        // 读取 视频 点击量 点赞量
        List<Videos> videosList = videosService.list();
        videosList.forEach(v -> {
            String videoId = String.valueOf(v.getId());
            Integer clickCount = v.getClickCount();
            Integer recommendCount = v.getRecommendCount();
            Timestamp clickUpdatedAt = v.getClickUpdatedAt();
            Timestamp recommendUpdatedAt = v.getRecommendUpdatedAt();
            // 获得 分数（带时间戳）
            Double clickScore = clickCount + ((clickUpdatedAt != null ? clickUpdatedAt.getTime() : 0) / 1e13);
            Double recommendScore = recommendCount + ((recommendUpdatedAt != null ? recommendUpdatedAt.getTime() : 0) / 1e13);
            // 存入 redis
            redisUtil.zsAdd(Const.VIDEO_CLICK_COUNT, videoId, clickScore);
            redisUtil.zsAdd(Const.VIDEO_RECOMMEND_COUNT, videoId, recommendScore);
        });

        // 读取 评论 点赞量
        List<Comment> comments = commentService.list();
        comments.forEach(v -> {
            String commentId = String.valueOf(v.getId());
            Integer recommendCount = v.getRecommendCount();
            Timestamp recommendUpdatedAt = v.getRecommendUpdatedAt();
            // 获得分数
            Double score = recommendCount + ((recommendUpdatedAt != null ? recommendUpdatedAt.getTime() : 0) / 1e13);
            // 存入redis
            redisUtil.zsAdd(Const.COMMENT_RECOMMEND_COUNT, commentId, score);
        });
    }
}

