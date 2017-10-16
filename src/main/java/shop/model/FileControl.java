package shop.model;

import java.io.InputStream;

public interface FileControl {

    String save(String name, InputStream stream);

    byte[] read(String name);

    Boolean remove(String name);
}