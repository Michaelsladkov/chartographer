package com.msladkov.chartographer.service.image;

import com.msladkov.chartographer.service.image.exceptions.ImageNotPresentException;
import com.msladkov.chartographer.service.image.exceptions.InvalidContentFolderPathException;
import com.msladkov.chartographer.service.image.exceptions.InvalidImageException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BMPFileManagerServiceImpl implements BMPFileManagerService{
    private String pathToContentFolder = "content";

    private final List<File> names;

    public BMPFileManagerServiceImpl() throws InvalidContentFolderPathException {
        File contentFolder = new File(pathToContentFolder);
        if (!contentFolder.isDirectory() || !contentFolder.canRead() || !contentFolder.canWrite()) {
            throw new InvalidContentFolderPathException();
        }
        File[] bmpFiles = contentFolder.listFiles(f -> f.getName().endsWith(".bmp"));
        if (bmpFiles == null) {
            throw new InvalidContentFolderPathException();
        }
        names = new ArrayList<>(Arrays.asList(bmpFiles));
    }

    @Override
    public BufferedImage getImageFromBMP(int id) throws ImageNotPresentException, InvalidImageException {
        File bmpFile = new File(getFileName(id));
        if (!bmpFile.exists() || !bmpFile.isFile() || !bmpFile.canRead() || !bmpFile.canWrite()) {
            throw new ImageNotPresentException();
        }
        BufferedImage ret;
        try {
            ret = ImageIO.read(bmpFile);
        } catch (IOException e) {
            throw new InvalidImageException(e.getMessage());
        }
        return ret;
    }

    @Override
    public void saveImage(int id, BufferedImage image) throws IOException {
        File bmpFile = new File(getFileName(id));
        bmpFile.createNewFile();
        ImageIO.write(image, "BMP", bmpFile);
    }

    @Override
    public void deleteImage(int id) throws ImageNotPresentException {
        File bmpFile = new File(getFileName(id));
        if (!bmpFile.exists()) {
            throw new ImageNotPresentException();
        }
        bmpFile.delete();
    }

    protected String getFileName(int id) {
        return pathToContentFolder + "/" + id + ".bmp";
    }

    public void setPathToContentFolder(String newPath) {
        pathToContentFolder = newPath;
        System.err.println(newPath);
    }

    @Override
    public List<Integer> getIds() {
        return names.stream().map(File::getName).
                map(str -> str.split(".bmp")[0]).
                map(Integer::parseInt).collect(Collectors.toList());
    }
}
