package com.msladkov.chartographer.service.image;

import com.msladkov.chartographer.service.image.exceptions.FragmentOutOfImageException;
import com.msladkov.chartographer.service.image.exceptions.ImageNotPresentException;
import com.msladkov.chartographer.service.image.exceptions.InvalidImageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {
    private final BMPFileManagerService fileManagerService;
    private int maxId;

    public ImageServiceImpl (@Autowired BMPFileManagerService fileManagerService) {
        this.fileManagerService = fileManagerService;
        maxId = fileManagerService.getIds().stream().max(Integer::compare).orElse(0);
    }

    @Override
    public BufferedImage getFragment(int id, int x, int y, int width, int height)
            throws ImageNotPresentException, InvalidImageException {
        BufferedImage image = fileManagerService.getImageFromBMP(id);
        BufferedImage ret = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        width = Math.min(width, image.getWidth() - x);
        height = Math.min(height, image.getHeight() - y);
        int scansize = width * height;
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
    public void deleteImage(int id) throws ImageNotPresentException {
        fileManagerService.deleteImage(id);
    }

    @Override
    public void setFragment(int id, int x, int y, BufferedImage fragment)
            throws InvalidImageException, ImageNotPresentException, FragmentOutOfImageException, IOException {
        BufferedImage image = fileManagerService.getImageFromBMP(id);
        if (x > image.getWidth() || y > image.getHeight()) {
            throw new FragmentOutOfImageException();
        }
        fragment = fragment.getSubimage(0, 0,
                Math.min(image.getWidth() - x, fragment.getWidth()),
                Math.min(image.getHeight() - y, fragment.getHeight()));
        int scansize = fragment.getHeight() * fragment.getHeight();
        image.setRGB(x, y, fragment.getWidth(), fragment.getHeight(),
                fragment.getRGB(0, 0, fragment.getWidth(), fragment.getHeight(), null, 0,
                        scansize), 0, scansize);
        fileManagerService.saveImage(id, image);
    }
}
