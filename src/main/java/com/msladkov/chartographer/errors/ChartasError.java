package com.msladkov.chartographer.errors;

import org.springframework.http.HttpStatus;

public class ChartasError {
    private final HttpStatus status;
    private final String message;

    public ChartasError (HttpStatus status, String message) {
        this.message = message;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
