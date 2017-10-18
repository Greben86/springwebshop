package shop.model.impl;

import shop.model.ImageControl;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class ImageControlImpl implements ImageControl {
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
            return e.getMessage();
        }
    }

    private File getFile(String name) {
        File f = null;
        if (!name.isEmpty()) {
            f = new File(path + name);
        } else {
            Resource resource = new ClassPathResource("/images/noimage.png");
            try {
				f = resource.getFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return f;
    }

    @Override
    public byte[] read(String name) {
        byte[] buff = null;
        File f = getFile(name);        
        if (f!=null) {
            if (f.exists()) {
                try (FileInputStream fis = new FileInputStream(f);
                    BufferedInputStream in = new BufferedInputStream(fis);) {
                    buff = new byte[(int) f.length()];
                    in.read(buff, 0, buff.length);            
                } catch (IOException e) {
                    e.printStackTrace();
                }
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