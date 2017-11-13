package shop.model;

import shop.entity.Good;
import java.io.InputStream;

public interface ImageControl {

    String saveFile(Good good, InputStream stream);

    byte[] readFile(Good good);

    String removeFile(Good good);
}