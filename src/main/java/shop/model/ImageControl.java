package shop.model;

import shop.entity.Good;
import java.io.InputStream;

public interface ImageControl {

    /**
     * Save image into hard-disk
     * @param good - entity good
     * @param stream - file input stream
     */
    String saveFile(Good good, InputStream stream);

    /**
     * Read image for hard-disk
     * @param good - entity good
     */
    byte[] readFile(Good good);

    /**
     * Remove image for har-disk
     * @param good - entity good
     */
    String removeFile(Good good);
}