package com.msladkov.chartographer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
