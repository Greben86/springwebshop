package shop.model.impl;

import shop.model.ImageControl;
import shop.entity.Good;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.apache.log4j.Logger;

public class ImageControlImpl implements ImageControl {
    private static final Logger LOG = Logger.getLogger(ImageControlImpl.class);
    private String path;

    private ImageControlImpl () {}

    public ImageControlImpl (String path) {
        this.path = path;
    }

    @Override
    public String saveFile (String filename, InputStream stream) {
        try (FileOutputStream fos = new FileOutputStream(path + filename);) {
            byte[] bytes = new byte[1024];
            int read = 0;
            while ((read = stream.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
            return "You successfully uploaded file=" + path + filename;
        } catch (IOException e) {
            LOG.error("something going wrong " + e);
            return "You failed uploaded file=" + path + filename;            
        }
    }

    private File getFile(String filename, String filedefault) {
        File f = new File(path + filename);
        if (!f.exists()||!f.isFile()) {
            Resource resource = new ClassPathResource("/images/"+filedefault);
            try {
                return resource.getFile();
            } catch (IOException e) {
                LOG.error("something going wrong " + e);
                return null;
            }
        } else {
            return f;
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
    public String removeFile(String filename) {
        File file = new File(path + filename);
        if (file.exists()&&file.isFile()) {
            return "Clear image "+filename+(file.delete()?" is Ok":" is Fail");
        } else {
            return "Clear image is fail, file not exist";
        }      
    }
}