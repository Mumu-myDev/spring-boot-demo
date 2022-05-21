package com.xkcoding.async.config;

import com.xkcoding.async.common.NamedThreadFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;


/***
 * 一般情况下：
 * cpuNums=cpu核数
 * IO密集型=2 cpuNums
 * CPU密集型=cpuNums+1
 *
 * 公式：
 * threadsNums=cpuNums * cpuUse * (1+w/c)
 * 最佳线程数 =CPU 核数 * [ 1 +（I/O 耗时 / CPU 耗时）]
 * cpuNums=CPU核心数
 * cpuUse=cpu使用率，0~1
 * W/C=等待时间与计算时间的比率
 */
@EnableAsync
@Configuration
@Log4j2
public class TaskPoolConfig {

    /**
     * 获取当前系统的CPU 数目
     */
    int cpuNums = Runtime.getRuntime().availableProcessors();
    /**
     * 线程池的最大线程数
     */
    private final int maximumPoolSize = cpuNums * 2;


    @Bean(value = "httpApiThreadPool")
    public ExecutorService buildHttpApiThreadPool() {

        int corePoolSize = 10;
        log.info("TreadPoolConfig创建线程数:" + corePoolSize + ",最大线程数:" + maximumPoolSize);
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 0L,
            TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100), new NamedThreadFactory("httpApi"));
    }

    @Bean
    public Executor taskExecutor1() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(10);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("自定义-线程池-1-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    @Bean
    public Executor taskExecutor2() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(10);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("自定义-线程池-2-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}

