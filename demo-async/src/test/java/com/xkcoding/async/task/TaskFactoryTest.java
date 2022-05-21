package com.xkcoding.async.task;

import com.xkcoding.async.SpringBootDemoAsyncApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * <p>
 * 测试任务
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-29 10:49
 */
@Slf4j
public class TaskFactoryTest extends SpringBootDemoAsyncApplicationTests {
    @Autowired
    private TaskFactory task;

    @Resource(name = "asyncDataPanelExecutor")
    ThreadPoolExecutor threadPool;

    /**
     * 测试异步任务
     */
    @Test
    public void asyncTaskTest2() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        Future<Integer> submit1 = threadPool.submit(() -> {
            Thread.currentThread().setName("Data");
            Thread.sleep(500);
            log.info("123");
            return 123;
        });
        Future<Integer> submit2 = threadPool.submit(() -> {
            Thread.sleep(400);
            log.info("456");
            return 456;
        });
        Future<Integer> submit3 = threadPool.submit(() -> {
            Integer y = 0 / 10;
            System.out.println(y);
            log.info("789");
            return 789;
        });

        Integer integer1 = submit1.get();
        Integer integer2 = submit2.get();
        Integer integer3 = submit3.get();
        log.info(" task 结果" + integer1 + "----1");
        log.info(" task 结果" + integer2 + "----1");
        log.info(" task 结果{}----1", integer3);


        long end = System.currentTimeMillis();
        log.info("异步任务全部执行结束，总耗时：{} 毫秒", (end - start));
    }
    @Test
    public void asyncTaskTest() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        Future<Boolean> asyncTask1 = task.asyncTask1();
        Future<Boolean> asyncTask2 = task.asyncTask2();
        Future<Boolean> asyncTask3 = task.asyncTask3();

        // 调用 get() 阻塞主线程
        asyncTask1.get();
        asyncTask2.get();
        asyncTask3.get();
        long end = System.currentTimeMillis();

        log.info("异步任务全部执行结束，总耗时：{} 毫秒", (end - start));
    }
    /**
     * 测试同步任务
     */
    @Test
    public void taskTest() throws InterruptedException {
        long start = System.currentTimeMillis();
        task.task1();
        task.task2();
        task.task3();
        long end = System.currentTimeMillis();

        log.info("同步任务全部执行结束，总耗时：{} 毫秒", (end - start));
    }
}
