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
    public String saveFile (Good good, InputStream stream) {
        if (good==null) {
            return "Good not found"; 
        } else {
            try (FileOutputStream fos = new FileOutputStream(path + good.getFilename());) {
                byte[] bytes = new byte[1024];
                int read = 0;
                while ((read = stream.read(bytes)) != -1) {
                    fos.write(bytes, 0, read);
                }
                return "You successfully uploaded file=" + path + good.getFilename();
            } catch (IOException e) {
                LOG.error("something going wrong " + e);
                return "You failed uploaded file=" + path + good.getFilename();            
            }
        }        
    }

    private File getFile(Good good) {
        if (good==null) {
            return null;
        } else {
            File f = new File(path + good.getFilename());
            if (!f.exists()||!f.isFile()) {
                Resource resource = new ClassPathResource(good.getFolder() ? "/images/noimagefolder.png" : "/images/noimagegood.png");
                try {
                    f = resource.getFile();
                } catch (IOException e) {
                    LOG.error("something going wrong " + e);
                }
            }
            return f;
        }
    }

    @Override
    public byte[] readFile(Good good) {
        byte[] buff = null;
        File f = getFile(good);
        if (f!=null) {
            try (FileInputStream fis = new FileInputStream(f);
                BufferedInputStream in = new BufferedInputStream(fis);) {
                buff = new byte[(int) f.length()];
                in.read(buff, 0, buff.length);
            } catch (IOException e) {
                LOG.error("something going wrong " + e);
            }
        }
        return buff;
    }

    @Override
    public Boolean removeFile(Good good) {
        if (good==null) {
            return false;
        } else {
            File file = new File(path + good.getFilename());
            
            if (file.exists()&&file.isFile()) {
                return file.delete();
            } else {
                return false;
            }
        }        
    }
}