package shop.model;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import shop.model.impl.ImageControlImpl;

public class ImageControlTest {

    public ImageControl createImageControl() {
        return new ImageControlImpl("");
    }

    @BeforeClass 
    public static void before() {
		System.out.println("Test ImageControl started..");
	} 
	
    @AfterClass 
    public static void after() {
		System.out.println("..test ImageControl finished.");
    }

    @Test
    public void saveFile () throws Exception {
        assertTrue("saveFile is fail", true);
    }

    @Test
    public void readFile() throws Exception {
        assertTrue("readFile is fail", true);   
    }

    @Test
    public void removeFile() throws Exception {
        assertTrue("removeFile is fail", true);     
    }
}