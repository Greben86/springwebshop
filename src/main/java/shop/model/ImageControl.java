package shop.model;

import java.io.File;
import java.io.InputStream;

public interface ImageControl {

    File getDirectory(String subfolder);
    
    /**
     * Save image into hard-disk
     * @param filename - name file
     * @param stream - file input stream
     */
    Boolean saveFile(String filename, InputStream stream);
    
    /**
     * Save image into hard-disk
     * @param file - file object
     * @param stream - file input stream
     */
    Boolean saveFile(File file, InputStream stream);

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
    Boolean removeFile(String filename);
}
