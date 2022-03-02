package com.msladkov.chartographer.controllers;

import com.msladkov.chartographer.service.image.ImageService;
import com.msladkov.chartographer.service.image.exceptions.FragmentOutOfImageException;
import com.msladkov.chartographer.service.image.exceptions.ImageNotPresentException;
import com.msladkov.chartographer.service.image.exceptions.InvalidImageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/chartas")
public class ChartasController {

    private ImageService imageService;

    @Autowired
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    @ExceptionHandler( {IOException.class, InvalidImageException.class})
    public void ioExceptionHandler(HttpServletResponse response) {
         try {
             response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "internal error has been occured");
         } catch (IOException e) {
             e.printStackTrace();
         }
    }

    @ExceptionHandler( {ImageNotPresentException.class})
    public void imageNotPresentExceptionHandler(HttpServletResponse response) {
        try {
            response.sendError(404, "image not present");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ExceptionHandler( {FragmentOutOfImageException.class})
    public void fragmentOutOfImageExceptionHandler(HttpServletResponse response) {
        try {
            response.sendError(400, "Requested fragment is out of image");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping
    public ResponseEntity createImage(@RequestParam int width, @RequestParam int height) throws IOException {
        if (width <= 0 || height <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Width and height should be positive");
        }
        return ResponseEntity.status(HttpStatus.OK).body(imageService.createImage(width, height));
    }

    @PostMapping (value = "/{id}/")
    public void uploadFragment(@PathVariable int id, @RequestParam int x, @RequestParam int y,
                               @RequestParam int width, @RequestParam int height, @RequestBody byte[] img) throws IOException, ImageNotPresentException, FragmentOutOfImageException, InvalidImageException {
        ByteArrayInputStream stream = new ByteArrayInputStream(img);
        BufferedImage fragment = ImageIO.read(stream);
        imageService.setFragment(id, x, y, fragment);
    }

    @GetMapping(value = "/{id}/", produces = "image/bmp")
    public @ResponseBody byte[] getFragment(@PathVariable int id, @RequestParam int x, @RequestParam int y,
                                            @RequestParam int width, @RequestParam int height)
            throws InvalidImageException, ImageNotPresentException, IOException, FragmentOutOfImageException {
        BufferedImage fragment = imageService.getFragment(id, x, y, width, height);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(fragment, "BMP", outputStream);
        return outputStream.toByteArray();
    }

    @DeleteMapping(value = "/{id}")
    public void deleteImage (@PathVariable int id) throws ImageNotPresentException {
        imageService.deleteImage(id);
    }
}
