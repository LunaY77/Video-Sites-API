package com.iflove.listener;

import com.iflove.service.LikeService;
import com.iflove.utils.RedisUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StartEndListener implements ApplicationRunner, DisposableBean {
    @Resource
    LikeService likeService;
    @Resource
    RedisUtil redisUtil;
    @Resource
    LettuceConnectionFactory lettuceConnectionFactory;

    @Override
    public void destroy() throws Exception {
        log.info("====================================关闭====================================");
        try {
            if (!lettuceConnectionFactory.isRunning()) {
                lettuceConnectionFactory.start();
            }
            // 将redis中的数据一次性写入数据库
            likeService.transLikeFromRedis2DB();
            likeService.transCountValueFromRedis2DB();
            // 清空 redis 缓存
            redisUtil.flushAll();
        } catch (IllegalStateException e) {
            log.error("在关闭过程中尝试操作Redis失败", e);
        }
        log.info("系统关闭===========reids->数据库更新完毕=================");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("====================================数据库 初始化开始====================================");
        //将数据库中的数据写入redis
        likeService.transLikeFromDB2Redis();
        likeService.transCountValueFromDB2Redis();
        log.info("====================================已写入redis====================================");
    }
}
