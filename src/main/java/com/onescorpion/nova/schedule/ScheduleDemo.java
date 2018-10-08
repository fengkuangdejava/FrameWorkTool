package com.onescorpion.nova.schedule;

import com.alibaba.druid.sql.visitor.functions.Now;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleDemo {

    public final static long SECOND = 1 * 1000;
    private Logger logger = LoggerFactory.getLogger(ScheduleDemo.class);

    @Scheduled(fixedRate = SECOND * 10)
    public void printInfo(){
        logger.info("I'm live;");
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void printTime(){
        logger.info("nowTime is 12 ");
    }
}
