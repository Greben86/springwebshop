package shop.model;

import java.io.File;
import org.springframework.web.multipart.MultipartFile;

public interface ImageControl {

    File getDirectory(String subfolder);
    
    /**
     * Save image into hard-disk
     * @param filename - name file
     * @param file - file input multipartfile
     */
    Boolean saveFile(String filename, MultipartFile file);
    
    /**
     * Save image into hard-disk
     * @param newfile - file object
     * @param file - file input multipartfile
     */
    Boolean saveFile(File newfile, MultipartFile file);

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
