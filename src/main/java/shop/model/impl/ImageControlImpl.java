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

public class ImageControlImpl implements ImageControl {
    private static final Logger LOG = Logger.getLogger(ImageControlImpl.class);
    private String path;

    private ImageControlImpl () {}

    public ImageControlImpl (String path) {
        this.path = path;
    }

    @Override
    public File getDirectory(String subfolder) {
        return new File(path + subfolder);        
    }

    @Override
    public Boolean saveFile (String filename, InputStream stream) {
        File file = new File(path + filename);
        return saveFile(file, stream);
    }
    
    @Override
    public Boolean saveFile (File file, InputStream stream) {
        try (FileOutputStream fos = new FileOutputStream(file);) {
            byte[] bytes = new byte[1024];
            int read = 0;
            while ((read = stream.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
            return true; 
        } catch (IOException e) {
            LOG.error("something going wrong " + e);
            return false;            
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
    public Boolean removeFile(String filename) {
        File file = new File(path + filename);
        if (file.exists()&&file.isFile()) {
            return file.delete(); 
        } else {
            return false; 
        }      
    }
}
