package com.github.ireh.mtp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class Task {
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<String> process(List<String> data) {
        log.info("Start process,{}", data.size());

        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        StringBuilder result = new StringBuilder();
        for (String datum : data) {
            result.append(datum.replace("x", "*")
                    .replace("b", "B")).append("\n");
        }
        return CompletableFuture.completedFuture(result.toString());
    }
}
