package com.iflove.listener;

import com.iflove.service.LikeService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */
@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StartEndListener {
    @Resource
    LikeService likeService;

    public StartEndListener(){
        log.info("开始数据初始化");
    }

    // 服务器开启
    @PostConstruct
    public void init() throws Exception {
        log.info("数据库 初始化开始");
        //将数据库中的数据写入redis

        log.info("已写入redis");
    }

    // 服务器关闭
    @PreDestroy
    public void afterDestroy(){
        log.info("关闭====================================");
        //将redis中的数据一次性写入数据库
        likeService.transLikeFromRedis2DB();
        likeService.transCountValueFromRedis2DB();
        log.info("系统关闭===========reids->数据库更新完毕=================");
    }

}
