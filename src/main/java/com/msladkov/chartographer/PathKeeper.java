package com.msladkov.chartographer;

/**
 * Singleton to keep path parameter between services and main class
 */
public class PathKeeper {
    private static PathKeeper instance = null;
    private final String path;

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
