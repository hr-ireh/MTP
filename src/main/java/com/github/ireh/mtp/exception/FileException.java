package com.github.ireh.mtp.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileException extends RuntimeException {
    private final String message;

    public FileException(String message) {
        super(message);
        this.message = message;
    }
}