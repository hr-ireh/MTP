package com.github.ireh.mtp.controller;

import com.github.ireh.mtp.service.FileProcessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileProcessController {
    private final FileProcessService fileProcessService;

    public FileProcessController(FileProcessService fileProcessService) {
        this.fileProcessService = fileProcessService;
    }

    @PostMapping(value = "/v1/file/process",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadSingleFile(@RequestParam("file") MultipartFile file) {
        fileProcessService.process(file);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}