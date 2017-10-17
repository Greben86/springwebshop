package shop.model.impl;

import shop.model.FileControl;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class FileControlImpl implements FileControl {
    private String path;

    private FileControlImpl () {}

    public FileControlImpl (String path) {
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

    private File getFile(String name) throws IOException {
        if (!name.equals("")) {
            return new File(path + name);
        } else {
            Resource resource = new ClassPathResource("/images/noimage.png");
            return resource.getFile();
        }
    }

    @Override
    public byte[] read (String name) {
        File f;
		try {
			f = getFile(name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
            e.printStackTrace();
            return null;
		}
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

    @Override
    public Boolean remove (String name) {
        File file = new File(path + name);
    
        return file.delete();
    }

}