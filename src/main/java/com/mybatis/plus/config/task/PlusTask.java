package com.mybatis.plus.config.task;

import com.mybatis.plus.utils.ThreadPoolManager;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PlusTask {
    Logger logger = LoggerFactory.getLogger(PlusTask.class);


    @Value("${conf.auto-task}")
    Boolean autoTask;

    private final int delay = 60 * 60 * 1000;
 
    // 第一次延迟1秒执行，当执行完后7100秒再执行
    @Scheduled(initialDelay = delay, fixedDelay = 2*60*60*1000 )
    public void remove(){
        if (!autoTask) {
            return;
        }
        logger.info("===开始执行定时任务===");
        ThreadPoolManager threadPoolManager = ThreadPoolManager.newInstance();
        try {

        } catch (Exception e) {
            logger.error("====定时任务异常====");
            e.printStackTrace();
        }
    }

//    @Scheduled(initialDelay = delay, fixedDelay = 12*delay )
    @Scheduled(cron = "0 0 2 * * ?") //每天凌晨两点执行
    public void handleGather(){
        if (!autoTask) {
            return;
        }
        logger.info("===开始执行定时任务===");
        ThreadPoolManager threadPoolManager = ThreadPoolManager.newInstance();
        try {

        } catch (Exception e) {
            logger.error("====定时任务异常====");
            e.printStackTrace();
        }
    }
}