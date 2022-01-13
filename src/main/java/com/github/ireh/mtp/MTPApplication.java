package com.github.ireh.mtp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@SpringBootApplication
public class MTPApplication {
    public static void main(String[] args) {
        SpringApplication.run(MTPApplication.class, args);
    }

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("AsyncPool-");
        executor.setDaemon(true);
        executor.setAllowCoreThreadTimeOut(true);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(0);
        executor.initialize();
        return executor;
    }

    @Bean(name = "simpleAsyncTaskExecutor")
    public Executor simpleAsyncTaskExecutor() {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("AsyncSimple-");
        executor.setDaemon(true);
        return executor;
    }
}