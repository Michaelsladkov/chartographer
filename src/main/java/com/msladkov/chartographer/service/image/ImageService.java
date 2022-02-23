package com.msladkov.chartographer.service.image;

import com.msladkov.chartographer.service.image.exceptions.FragmentOutOfImageException;
import com.msladkov.chartographer.service.image.exceptions.ImageNotPresentException;
import com.msladkov.chartographer.service.image.exceptions.InvalidImageException;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImageService {
    BufferedImage getFragment(int id, int x, int y, int width, int height) throws ImageNotPresentException, InvalidImageException;
    int createImage(int width, int height) throws IOException;
    void deleteImage(int id) throws ImageNotPresentException;
    void setFragment(int id, int x, int y, BufferedImage fragment) throws InvalidImageException, ImageNotPresentException, FragmentOutOfImageException, IOException;
}