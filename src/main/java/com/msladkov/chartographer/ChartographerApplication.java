package com.msladkov.chartographer;

import com.msladkov.chartographer.service.image.BMPFileManagerService;
import com.msladkov.chartographer.service.image.BMPFileManagerServiceImpl;
import com.msladkov.chartographer.service.image.exceptions.InvalidContentFolderPathException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Comparator;

@SpringBootApplication
public class ChartographerApplication {

    public static String path = "content";

    public static void main(String[] args) throws InvalidContentFolderPathException, IOException {
        if (args.length > 0) {
            path = args[0];
        }
        //SpringApplication.run(ChartographerApplication.class, args);
        BMPFileManagerService fileManagerService = new BMPFileManagerServiceImpl();
        fileManagerService.saveImage(1, new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR));
        int maxId = fileManagerService.getIds().stream().max(Integer::compare).orElse(0);
        System.out.println(maxId);
        fileManagerService.saveImage(++maxId, new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR));
    }

}
