package com.github.ireh.mtp.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class ApiError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;
    private List<String> errors;
    private String referenceId;

    public ApiError(String message, List<String> errors, String referenceId) {
        this.message = message;
        this.errors = errors;
        this.referenceId = referenceId;
    }

    public ApiError(String message, String error, String referenceId) {
        this.message = message;
        this.errors = Collections.singletonList(error);
        this.referenceId = referenceId;
    }

    public ApiError(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public ApiError(String message, String error) {
        this.message = message;
        this.errors = Collections.singletonList(error);
    }
}