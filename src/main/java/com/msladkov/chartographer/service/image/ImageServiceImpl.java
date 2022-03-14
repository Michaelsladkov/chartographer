package com.msladkov.chartographer.service.image;

import com.msladkov.chartographer.service.image.exceptions.FragmentOutOfImageException;
import com.msladkov.chartographer.service.image.exceptions.ImageNotPresentException;
import com.msladkov.chartographer.service.image.exceptions.InvalidImageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Implementation of image service
 */
@Service
public class ImageServiceImpl implements ImageService {
    private final FileManagerService fileManagerService;
    private int maxId;

    public ImageServiceImpl (@Autowired FileManagerService fileManagerService) {
        this.fileManagerService = fileManagerService;
        maxId = fileManagerService.getIds().stream().max(Integer::compare).orElse(0);
    }

    @Override
    public BufferedImage getFragment(int id, int x, int y, int width, int height)
            throws ImageNotPresentException, InvalidImageException, FragmentOutOfImageException {
        BufferedImage image = fileManagerService.getImageFromFile(id);
        if (!checkRequestParameters(image, x, y, width, height)) {
            throw new FragmentOutOfImageException();
        }
        BufferedImage ret = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        width = Math.min(width, image.getWidth() - x);
        height = Math.min(height, image.getHeight() - y);
        int scansize = width;
        ret.setRGB(0, 0, width, height,
                image.getRGB(x, y, width, height, null, 0, scansize),0, scansize);
        return ret;
    }

    @Override
    public int createImage(int width, int height) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        fileManagerService.saveImage(++maxId, image);
        return maxId;
    }

    @Override
    public void deleteImage(int id) throws ImageNotPresentException, IOException {
        fileManagerService.deleteImage(id);
    }

    @Override
    public void setFragment(int id, int x, int y, int width, int height, BufferedImage fragment)
            throws InvalidImageException, ImageNotPresentException, FragmentOutOfImageException, IOException {
        BufferedImage image = fileManagerService.getImageFromFile(id);
        if (x > image.getWidth() || y > image.getHeight()) {
            throw new FragmentOutOfImageException();
        }
        fragment = fragment.getSubimage(0, 0,
                Math.min(image.getWidth() - x, width),
                Math.min(image.getHeight() - y, height));
        int scansize = fragment.getWidth();
        image.setRGB(x, y, fragment.getWidth(), fragment.getHeight(),
                fragment.getRGB(0, 0, fragment.getWidth(), fragment.getHeight(), null, 0,
                        scansize), 0, scansize);
        fileManagerService.saveImage(id, image);
    }

    /**
     * Method for checking is starting point out of image or not and width and heights are positive
     * @param image image to check
     * @param x x-coordinate of point to check
     * @param y y-coordinate of point to check
     * @param width width to check
     * @param height height to check
     * @return true if parameters are valid, false otherways
     */
    protected boolean checkRequestParameters(BufferedImage image, int x, int y, int width, int height) {
        if (width < 0 || height < 0) return false;
        if (x < 0 || y < 0) return false;
        if (x > image.getWidth()) return false;
        return y <= image.getHeight();
    }
}
