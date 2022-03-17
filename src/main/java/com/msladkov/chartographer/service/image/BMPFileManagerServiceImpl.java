package com.msladkov.chartographer.service.image;

import com.msladkov.chartographer.PathKeeper;
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

/**
 * Class to work with BMP files: to obtain BufferedImage objects from files, save these object to files, delete files etc.
 */
@Service
public class BMPFileManagerServiceImpl implements FileManagerService {

    private static final String DEFAULT_PATH = "content";

    private final List<File> names;

    private String pathToContentFolder;

    public BMPFileManagerServiceImpl() throws InvalidContentFolderPathException {
        pathToContentFolder = PathKeeper.getInstance().getPath();
        if (pathToContentFolder == null) pathToContentFolder = DEFAULT_PATH;
        File contentFolder = new File(pathToContentFolder);
        if (contentFolder.exists()) {
            if (!contentFolder.isDirectory() || !contentFolder.canRead() || !contentFolder.canWrite()) {
                throw new InvalidContentFolderPathException();
            }
        } else {
            if (!contentFolder.mkdirs()) throw new InvalidContentFolderPathException();
        }
        File[] bmpFiles = contentFolder.listFiles(f -> f.getName().endsWith(".bmp"));
        if (bmpFiles == null) {
            throw new InvalidContentFolderPathException();
        }
        names = new ArrayList<>(Arrays.asList(bmpFiles));
    }

    @Override
    public BufferedImage getImageFromFile(int id) throws ImageNotPresentException, InvalidImageException {
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
    public void deleteImage(int id) throws ImageNotPresentException, IOException {
        File bmpFile = new File(getFileName(id));
        if (!bmpFile.exists()) {
            throw new ImageNotPresentException();
        }
        if (!bmpFile.delete()) throw new IOException("failed to delete image");
    }

    /**
     * Method to build file name from id
     *
     * @param id image's id
     * @return file name
     */
    protected String getFileName(int id) {
        return pathToContentFolder + "/" + id + ".bmp";
    }

    @Override
    public List<Integer> getIds() {
        return names.stream().map(File::getName).
                map(str -> str.split(".bmp")[0]).
                map(Integer::parseInt).collect(Collectors.toList());
    }
}
