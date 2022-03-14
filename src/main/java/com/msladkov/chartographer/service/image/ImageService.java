package com.msladkov.chartographer.service.image;

import com.msladkov.chartographer.service.image.exceptions.FragmentOutOfImageException;
import com.msladkov.chartographer.service.image.exceptions.ImageNotPresentException;
import com.msladkov.chartographer.service.image.exceptions.InvalidImageException;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Interface for service to work with images and fragments
 */
public interface ImageService {
    /**
     * Get fragment from image
     * @param id  image's id
     * @param x  x-coordinate of starting point of the fragment
     * @param y  y-coordinate of starting point of the fragment
     * @param width  width of requested fragment in pixels
     * @param height  width of requested fragment in pixels
     * @return BufferedImage object containing requested fragment
     * @throws ImageNotPresentException if passed id doesn't match any image
     * @throws InvalidImageException if reading image from file failed
     * @throws FragmentOutOfImageException if starting point is out of image corresponding to passed id
     */
    BufferedImage getFragment(int id, int x, int y, int width, int height) throws ImageNotPresentException, InvalidImageException, FragmentOutOfImageException;

    /**
     * Create new image of given size, create file for it
     * @param width width of image in pixels
     * @param height height of image in pixels
     * @return id of created image
     * @throws IOException if some error with file work has been occurred
     */
    int createImage(int width, int height) throws IOException;

    /**
     * Delete image and corresponding file
     * @param id image's id
     * @throws ImageNotPresentException if there is no image with such id
     * @throws IOException if some error with file work has been occurred
     */
    void deleteImage(int id) throws ImageNotPresentException, IOException;

    /**
     * Insert new image of given size to given positions of image
     * @param id image's id
     * @param x x-coordinate of point of insertion
     * @param y y-coordinate of point of insertion
     * @param width width of fragment being inserted in pixels
     * @param height width of fragment being inserted in pixels
     * @param fragment BufferedImage object containing fragment being inserted
     * @throws InvalidImageException if reading image from file failed
     * @throws ImageNotPresentException if there is no image with such id
     * @throws FragmentOutOfImageException if insertion point is out of image corresponding to passed id
     * @throws IOException if some error with file work has been occurred
     */
    void setFragment(int id, int x, int y, int width, int height, BufferedImage fragment) throws InvalidImageException, ImageNotPresentException, FragmentOutOfImageException, IOException;
}
