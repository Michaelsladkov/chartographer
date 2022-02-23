package com.msladkov.chartographer;

import com.msladkov.chartographer.service.image.BMPFileManagerService;
import com.msladkov.chartographer.service.image.BMPFileManagerServiceImpl;
import com.msladkov.chartographer.service.image.ImageService;
import com.msladkov.chartographer.service.image.ImageServiceImpl;
import com.msladkov.chartographer.service.image.exceptions.FragmentOutOfImageException;
import com.msladkov.chartographer.service.image.exceptions.ImageNotPresentException;
import com.msladkov.chartographer.service.image.exceptions.InvalidContentFolderPathException;
import com.msladkov.chartographer.service.image.exceptions.InvalidImageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Comparator;

@SpringBootApplication
public class ChartographerApplication {

    public static String path = "content";

    public static void main(String[] args) {
        if (args.length > 0) {
            path = args[0];
        }
        SpringApplication.run(ChartographerApplication.class, args);
    }

}
