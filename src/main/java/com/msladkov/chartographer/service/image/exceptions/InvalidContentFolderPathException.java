package com.msladkov.chartographer.service.image.exceptions;

public class InvalidContentFolderPathException extends Exception{
    public InvalidContentFolderPathException() {
        super("Content folder path is invalid");
    }
}
