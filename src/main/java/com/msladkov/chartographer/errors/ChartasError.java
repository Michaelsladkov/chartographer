package com.msladkov.chartographer.errors;

import org.springframework.http.HttpStatus;

/**
 * Class to represent errors for REST API.
 * Instances of this class are used to be returned from REST controller methods if any errors have been occurred
 */
public class ChartasError {
    private final HttpStatus status;
    private final String message;

    public ChartasError(HttpStatus status, String message) {
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
