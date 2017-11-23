package shop.model;

import shop.entity.Good;
import java.io.InputStream;

public interface ImageControl {

    /**
     * Save image into hard-disk
     * @param filename - name file
     * @param stream - file input stream
     */
    String saveFile(String filename, InputStream stream);

    /**
     * Read image for hard-disk
     * @param filename - name file
     * @param filedefault - name file of default
     */
    byte[] readFile(String filename, String filedefault);

    /**
     * Remove image for har-disk
     * @param filename - name file
     */
    String removeFile(String filename);
}