package com.msladkov.chartographer.service.image;

import com.msladkov.chartographer.service.image.exceptions.ImageNotPresentException;
import com.msladkov.chartographer.service.image.exceptions.InvalidImageException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public interface BMPFileManagerService {
    BufferedImage getImageFromBMP(int id) throws ImageNotPresentException, InvalidImageException;
    void saveImage(int id, BufferedImage image) throws IOException;
    void deleteImage(int id) throws ImageNotPresentException;
    void setPathToContentFolder(String newPath);
    List<Integer> getIds();
}
