package com.msladkov.chartographer.controllers;

import com.msladkov.chartographer.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/chartas")
public class ChartasController {

    private ImageService imageService;

    @Autowired
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    @ExceptionHandler( {IOException.class})
    public void ioExceptionHandler(HttpServletResponse response) {
         try {
             response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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

}
