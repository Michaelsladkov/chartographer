package com.msladkov.chartographer.service.image.exceptions;

public class InvalidImageException extends Exception {
    public InvalidImageException(String msg) {
        super("Image is invalid: \n" + msg);
    }
}
