package com.msladkov.chartographer.service.image;

import com.msladkov.chartographer.service.image.exceptions.ImageNotPresentException;
import com.msladkov.chartographer.service.image.exceptions.InvalidImageException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * Interface for service which works with files to store images
 * Can be implemented by many classes for many formats
 */
public interface FileManagerService {
    /**
     * Get BufferedImage object from file, using id for access
     *
     * @param id image's id
     * @return requested image
     * @throws ImageNotPresentException if there is no image with such id
     * @throws InvalidImageException    if image file is not correct
     */
    BufferedImage getImageFromFile(int id) throws ImageNotPresentException, InvalidImageException;

    /**
     * Save image to file
     *
     * @param id    image's id
     * @param image image to save
     * @throws IOException if error with file work has been occurred
     */
    void saveImage(int id, BufferedImage image) throws IOException;

    /**
     * Delete image file
     *
     * @param id image's id
     * @throws ImageNotPresentException if there is no image file with such id
     * @throws IOException              if error with file work has been occurred
     */
    void deleteImage(int id) throws ImageNotPresentException, IOException;

    /**
     * Get list of id's for all images present
     *
     * @return list of id's for all images present
     */
    List<Integer> getIds();
}
