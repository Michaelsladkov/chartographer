package com.msladkov.chartographer.service.image.exceptions;

public class ImageNotPresentException extends Exception {
    public ImageNotPresentException() {
        super("Image is not present");
    }

    public ImageNotPresentException(int id) {
        super("Image with id " + id + " is not present");
    }
}
