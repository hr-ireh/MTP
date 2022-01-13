package com.github.ireh.mtp.service;

import com.github.ireh.mtp.exception.FileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FileProcessService {
    private final Task task;

    @Value("${process.max-thread-count:1}")
    private int maxThreadCount;

    public FileProcessService(Task task) {
        this.task = task;
    }

    public String process(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileException("File is empty");
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new FileException("The file is not correct");
        }
        String type = originalFilename.substring(originalFilename.indexOf(".") + 1).toLowerCase();
        if ("txt".compareToIgnoreCase(type) != 0) {
            throw new FileException("File type is not allowed");
        }

        try {
            //Pre-process
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            List<String> dataRow = br.lines().collect(Collectors.toList());
            int threadCount = dataRow.size() / Math.min(dataRow.size(), maxThreadCount);
            Collection<List<String>> lists = divideList(dataRow, threadCount);

            //process
            List<CompletableFuture<String>> futureList = new ArrayList<>(lists.size());
            for (List<String> list : lists) {
                CompletableFuture<String> process = task.process(list);
                futureList.add(process);
            }

            //Data integration
            List<String> dataProcessed = futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
            String resultData = String.join("", dataProcessed);

            log.info(resultData);
            return resultData;
        } catch (Exception e) {
            throw new FileException("Could not upload file");
        }
    }

    private <T> Collection<List<T>> divideList(final List<T> data, final int chunkSize) {
        final AtomicInteger counter = new AtomicInteger();
        return data.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / chunkSize))
                .values();

    }
}