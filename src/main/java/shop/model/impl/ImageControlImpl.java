package shop.model.impl;

import shop.model.ImageControl;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import static java.util.Optional.ofNullable;

public class ImageControlImpl implements ImageControl {

    private static final Logger LOG = Logger.getLogger(ImageControlImpl.class);
    private String path;

    private ImageControlImpl() {
    }

    public ImageControlImpl(String path) {
        this.path = path;
    }

    @Override
    public File getDirectory(String subfolder) {
        return new File(path + subfolder);
    }
    
    private File getFile(String filename) {
        return new File(path + filename);
    }

    @Override
    public Boolean saveFile(String filename, MultipartFile file) {
        return ofNullable(file)
                .map(f -> saveFile(getFile(filename), f))
                .orElse(Boolean.FALSE);
    }

    @Override
    public Boolean saveFile(File newfile, MultipartFile file) {
        try (FileOutputStream fos = new FileOutputStream(newfile);
                InputStream is = file.getInputStream();) {
            byte[] bytes = new byte[1024];
            int read = 0;
            while ((read = is.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
            return true;
        } catch (IOException e) {
            LOG.error("something going wrong " + e);
            return false;
        }
    }

    private File getFile(String filename, String filedefault) {
        File file = getFile(filename);
        if (!file.exists() || !file.isFile()) {
            return getResourceFile(filedefault);
        } else {
            return file;
        }
    }

    @Override
    public byte[] readFile(String filename, String filedefault) {
        File f = getFile(filename, filedefault);
        try (FileInputStream fis = new FileInputStream(f);
                BufferedInputStream in = new BufferedInputStream(fis);) {
            byte[] buff = new byte[(int) f.length()];
            in.read(buff, 0, buff.length);
            return buff;
        } catch (IOException e) {
            LOG.error("something going wrong " + e);
            return null;
        }
    }

    @Override
    public Boolean removeFile(String filename) {
        File file = getFile(filename);
        return (file.exists() && file.isFile()) ? file.delete() : false;
    }

    private File getResourceFile(String filedefault) {
        Resource resource = new ClassPathResource(filedefault);
        try {
            return resource.getFile();
        } catch (IOException e) {
            LOG.error("something going wrong " + e);
            return null;
        }
    }

}
