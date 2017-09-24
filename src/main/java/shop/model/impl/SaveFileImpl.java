package shop.model.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import shop.model.SaveFile;

public class SaveFileImpl implements SaveFile {
    private String path;

    private SaveFileImpl() {}

    public SaveFileImpl(String path) {
        this.path = path;
    }

    @Override
    public String save(String name, InputStream stream) {
        try (FileOutputStream fos = new FileOutputStream(path + name);) {
            byte[] bytes = new byte[1024];
            int read = 0;
            while ((read = stream.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
            return "You successfully uploaded file=" + path + name;
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}