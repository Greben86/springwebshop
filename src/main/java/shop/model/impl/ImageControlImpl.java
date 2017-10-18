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
    public String save (String name, InputStream stream) {
        try (FileOutputStream fos = new FileOutputStream(path + name);) {
            byte[] bytes = new byte[1024];
            int read = 0;
            while ((read = stream.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
            return "You successfully uploaded file=" + path + name;
        } catch (IOException e) {
            LOG.error("something going wrong " + e);
            return "You failed uploaded file=" + path + name;            
        }
    }

    private File getFile(String name) {
        File f = new File(path + name);
        if (!f.exists()||!f.isFile()) {
            Resource resource = new ClassPathResource("/images/noimage.png");
            try {
				f = resource.getFile();
			} catch (IOException e) {
                LOG.error("something going wrong " + e);
			}
        }
        return f;
    }

    @Override
    public byte[] read(String name) {
        byte[] buff = null;
        File f = getFile(name);        
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
    public Boolean remove (String name) {
        File file = new File(path + name);

        return file.delete();
    }

}