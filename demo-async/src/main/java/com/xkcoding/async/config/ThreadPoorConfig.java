package com.xkcoding.async.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * @author aszer
 */
@Configuration
@Log4j2
@EnableAsync
public class ThreadPoorConfig  {


    @Bean(name = "asyncDataPanelExecutor")
    public ThreadPoolExecutor asyncDataPanelExecutor() {
        log.info("start async-dataPanel-Pool");
        return new ThreadPoolExecutor(10, 10, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(20), new ThreadPoolExecutor.CallerRunsPolicy());
    }

//    @Override
    public Executor getAsyncExecutor() {
        //线程池
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix("------");
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(25);
        taskExecutor.initialize();

        return taskExecutor;
    }


}
