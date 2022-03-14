package com.msladkov.chartographer;

import com.msladkov.chartographer.service.image.ImageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class ChartographerApplication {

    private static String path = "content";

    public static void main(String[] args) {
        if (args.length > 0) {
            path = args[0];
        }
        PathKeeper.createInstance(path);
        SpringApplication.run(ChartographerApplication.class, args);
    }
}
