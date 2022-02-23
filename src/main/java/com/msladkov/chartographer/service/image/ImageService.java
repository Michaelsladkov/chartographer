package com.msladkov.chartographer.service.image;

import java.awt.image.BufferedImage;

public interface ImageService {
    BufferedImage getFragment(int id, int x, int y, int width, int height);
    int createImage(int width, int height);
    void deleteImage(int id);
    void setFragment(int id, int x, int y, int width, int height);
}
