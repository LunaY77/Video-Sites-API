# Video-Sites-API
## 后端技术栈
* Spring Boot
* Spring Security
* MyBatis-plus
* MySQL
* Redis
* RabbitMQ

***
## 后端功能与技术点
### 技术点
* 使用雪花算法生成ID，安全性强
* 采用Mybatis-Plus作为持久层框架，使用更便捷
* 采用RabbitMQ积压短信发送任务，再由监听器统一处理
* 采用SpringSecurity作为权限校验框架，支持方法级别的授权(待解决?)，手动整合Jwt校验方案
* 采用Redis进行IP地址限流处理，防刷接口
* 采用Redis存储高频操作，缓解并发问题
* 视图层对象和数据层对象分离，编写工具方法利用反射快速互相转换 dto -> vo
* 自定义MessageHandler, 实现 vo 快速转换 结果集
* 错误和异常页面统一采用JSON格式返回，处理响应更统一
* 手动处理跨域，采用过滤器实现
* 大量使用反射，函数时编程，泛型编程简化代码，代码简洁高效
* 项目整体结构清晰，职责明确，注释全面

## 项目结构树
```
|
├─java
│  └─com
│      └─iflove
│          │  MainApplication.java                                          启动类
│          │  
│          ├─config
│          │      MybatisConfiguration.java                                 mybatis配置类
│          │      RabbitConfiguration.java                                  RabbitMQ配置类
│          │      RedisConfiguration.java                                   Redis配置类
│          │      SecurityConfiguration.java                                SpringSecurity配置类
│          │      WebConfiguration.java                                     Web配置类
│          │      
│          ├─controller
│          │  ├─exception
│          │  │      ValidationController.java                              异常页面Controller
│          │  │      
│          │  ├─interactive
│          │  │      CommentController.java                                 评论模块
│          │  │      LikeController.java                                    点赞模块
│          │  │      
│          │  ├─social
│          │  │      RelationController.java                                社交模块
│          │  │      
│          │  ├─user
│          │  │      AuthorizeController.java                               鉴权模块
│          │  │      UserController.java                                    用户模块
│          │  │      ValidationTestController.java                          校验模块（bug）
│          │  │      
│          │  └─video
│          │          VideoController.java                                  视频模块
│          │          
│          ├─entity
│          │  │  BaseData.java                                              对象转换工具
│          │  │  Const.java                                                 常量类
│          │  │  CountParams.java                                           点赞/点击信息
│          │  │  RestBean.java                                              结果集
│          │  │  ResultCodeEnum.java                                        错误代码枚举类
│          │  │  
│          │  ├─dto
│          │  │      Account.java                                           账户dto
│          │  │      Comment.java                                           评论dto
│          │  │      Followers.java                                         关注dto
│          │  │      LikesComment.java                                      评论点赞关系dto
│          │  │      LikesVideo.java                                        视频点赞关系dto
│          │  │      Role.java                                              账户权限dto
│          │  │      Videos.java                                            视频dto
│          │  │      
│          │  └─vo
│          │      ├─request                                                 
│          │      │      ConfirmResetVO.java                                验证重置信息vo
│          │      │      EmailRegisterVO.java                               邮箱注册信息vo
│          │      │      EmailResetVO.java                                  邮箱重置信息vo
│          │      │      VideoPostVO.java                                   视频上传vo
│          │      │      
│          │      └─response
│          │              AuthorizeVO.java                                  用户登录验证信息vo
│          │              CommentInfoVO.java                                评论信息vo
│          │              FollowInfoVO.java                                 关注信息vo
│          │              ListVO.java                                       列表信息vo
│          │              UserInfoVO.java                                   用户信息vo
│          │              VideoInfoVO.java                                  视频信息vo
│          │              
│          ├─filter
│          │      CorsFilter.java                                           跨域过滤器
│          │      FlowLimitFilter.java                                      限流过滤器
│          │      
│          ├─listener
│          │      MailQueueListener.java                                    邮件发送监听器
│          │      StartEndListener.java                                     服务器启动停止监听器
│          │      
│          ├─mapper
│          │      AccountMapper.java                                        用户Mapper
│          │      CommentMapper.java                                        评论Mapper
│          │      FollowersMapper.java                                      关注Mapper
│          │      LikesCommentMapper.java                                   评论点赞关系Mapper
│          │      LikesVideoMapper.java                                     视频点赞关系Mapper
│          │      VideosMapper.java                                         视频Mapper
│          │      
│          ├─security
│          │      JwtAuthenticationProvider.java                            用户凭证校验
│          │      JwtAuthenticationTokenFilter.java                         JWT-Token 过滤器
│          │      UserDetailsImpl.java                                      UserDetails实现类
│          │      UserDetailsServiceImpl.java                               UserDetailsService实现类
│          │      
│          ├─service
│          │  │  AccountService.java                                        用户Service
│          │  │  CommentService.java                                        评论Service
│          │  │  FollowersService.java                                      关注Service
│          │  │  LikesCommentService.java                                   评论点赞关系Service
│          │  │  LikeService.java                                           点赞Service
│          │  │  LikesVideoService.java                                     视频点赞关系Service
│          │  │  VideosService.java                                         视频Service
│          │  │  
│          │  └─impl
│          │          AccountServiceImpl.java                               用户Service实现类
│          │          CommentServiceImpl.java                               评论Service实现类
│          │          FollowersServiceImpl.java                             关注Service实现类
│          │          LikesCommentServiceImpl.java                          评论点赞关系Service实现类
│          │          LikeServiceImpl.java                                  点赞Service实现类
│          │          LikesVideoServiceImpl.java                            视频点赞关系Service实现类
│          │          VideosServiceImpl.java                                视频Service实现类
│          │          
│          └─utils
│                  FileConfig.java                                          文件读写配置类
│                  FileUtil.java                                            文件读写工具类
│                  FlowUtil.java                                            限流工具类
│                  JacksonMapper.java                                       Jackson映射类(Long -> String)
│                  JacksonUtil.java                                         Jackson工具类
│                  MessageHandler.java                                      消息处理工具类
│                  RedisUtil.java                                           Redis工具类
│                  
└─resources
    │  application.yaml                                                     服务器配置文件
    
                            

```

### 1. 用户模块
实现接口：
登录、注册、登出、重置密码、获取用户信息、上传/修改头像
* 采用SpringSecurity作为权限校验框架，使用 开源工具 [hutool](https://github.com/dromara/hutool) 协助操作 JWT-Token 完成权限校验
* 采用Redis存储注册/重置操作验证码，带过期时间控制
* 采用Redis存储登录白名单/黑名单，实现记住我/登出功能
* 采用RabbitMQ结合 SpringBoot Mail 框架实现发送邮件验证码
* 自定义 FileUtil，生成 uuid + 当前时间 作为文件名，按照年月日创建文件夹，存储头像文件

### 2. 视频模块
实现接口：
投稿视频，发布列表，搜索视频，浏览视频，热门排行榜
* 自定义 FileUtil，生成 uuid + 当前时间 作为文件名，按照年月日创建文件夹，存储视频文件
* 实现分页查询

### 3. 互动模块
实现接口:
点赞，点赞列表，评论，评论列表，删除评论
* 采用 Redis 处理高频操作，使用 zset 缓存 点击量，点赞量(当数量相同时，采用时间戳记录更新时间，新更新的更大)，使用 hash 存储 点赞关系
* 实现持久化存储，在关闭服务器时将 redis缓存 持久化存储至 MySQL
* 采用 dfs 算法，获得父评论的子评论数量，删除父评论时，同步删除其子评论

### 4. 社交模块
实现接口：
关注，关注列表，粉丝列表，朋友列表
* 采用内连接查询朋友列表

## 感想
函数时编程真好玩 ~~(真tm装b，希望我过几天回头还能看懂我的代码)~~

伟大，无需多言
``` Java
    /**
     * 持久化存储 点赞量，点击量
     */
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
            Timestamp update = new Timestamp((long) ((score - count) * 1e13));
            CountParams countParams = new CountParams(Long.parseLong(value), new Date(), count, update);
            T result = mapper.apply(countParams);
            lst.add(result);
        });
        service.accept(lst);
    }
```
