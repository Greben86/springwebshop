package shop.model.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import shop.model.ReadFile;

public class ReadFileImpl implements ReadFile {
    private String path;

    private ReadFileImpl() {}

    public ReadFileImpl(String path) {
        this.path = path;
    }

    @Override
    public byte[] read (String name) {
        File f = new File(path + name);
        try (FileInputStream fis = new FileInputStream(f);
            BufferedInputStream in = new BufferedInputStream(fis);) {
            byte[] buff = new byte[(int) f.length()];
            in.read(buff, 0, buff.length);
            return buff;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
		} 
    }
}