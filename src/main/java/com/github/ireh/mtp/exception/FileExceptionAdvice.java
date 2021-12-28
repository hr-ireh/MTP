package com.github.ireh.mtp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@ControllerAdvice
public class FileExceptionAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Object> handleFileNotFoundException(FileNotFoundException ex, HttpServletRequest request) {
        String referenceId = UUID.randomUUID().toString();
        String url = request.getMethod() + " " + request.getRequestURI();

        log.error("HandleFileNotFoundException referenceId: {} {}", referenceId, url, ex);

        ApiError apiError = new ApiError("File Not Found", Collections.singletonList(ex.getMessage()), referenceId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException ex, HttpServletRequest request) {
        String referenceId = UUID.randomUUID().toString();
        String url = request.getMethod() + " " + request.getRequestURI();

        log.error("HandleMaxSizeException referenceId: {} {}", referenceId, url, ex);

        ApiError apiError = new ApiError("File Size Exceeded", Collections.singletonList(ex.getMessage()), referenceId);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleMaxSizeException(Exception ex, HttpServletRequest request) {
        String referenceId = UUID.randomUUID().toString();
        String url = request.getMethod() + " " + request.getRequestURI();

        log.error("Exception referenceId: {} {}", referenceId, url, ex);

        ApiError apiError = new ApiError("Exceeded", Collections.singletonList(ex.getMessage()), referenceId);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(apiError);
    }
}