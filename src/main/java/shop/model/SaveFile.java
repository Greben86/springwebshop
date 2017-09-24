package shop.model;

import java.io.InputStream;

public interface SaveFile {
    String save(String name, InputStream stream);
}