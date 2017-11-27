package shop.model;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
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

    
}