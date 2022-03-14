package com.msladkov.chartographer;

public class PathKeeper {
    private String path;

    private static PathKeeper instance = null;

    private PathKeeper(String path) {
        this.path = path;
    }

    public static PathKeeper createInstance(String path) {
        if (instance == null) {
            return instance = new PathKeeper(path);
        }
        return null;
    }

    public static PathKeeper getInstance() {
        return instance;
    }

    public String getPath() {
        return path;
    }
}
