package com.github.ireh.mtp.service;

import com.github.ireh.mtp.exception.FileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FileProcessService {

    public void process(MultipartFile file) {
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
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            List<String> collect = br.lines().collect(Collectors.toList());

            for (String line : collect) {
                //TODO Process
                log.info(line);
            }

        } catch (Exception e) {
            throw new FileException("Could not upload file");
        }
    }
}