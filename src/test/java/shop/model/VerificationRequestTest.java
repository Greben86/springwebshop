package shop.model;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import shop.model.impl.VerificationRequestImpl;

public class VerificationRequestTest {
    private final String key = "052b15e78378eb6c82164b63c27be0b1";

    private VerificationRequest createVerificationRequest() {
        final VerificationRequest verificationRequest = new VerificationRequestImpl(key);
        return verificationRequest;
    }

    @BeforeClass 
    public static void before() {
		System.out.println("Test VerificationRequest started..");
	} 
	
    @AfterClass
    public static void after() {
		System.out.println("..test VerificationRequest finished.");
    }

    @Test
    public void verifyTrue() throws Exception {
        final VerificationRequest verificationRequest = createVerificationRequest();
        assertTrue("verify always false", verificationRequest.verify(key));
    }

    @Test
    public void verifyFalse() throws Exception {
        final VerificationRequest verificationRequest = createVerificationRequest();
        String key2 = "098f6bcd4621d373cade4e832627b4f6";
        assertFalse("verify always true", verificationRequest.verify(key2));
    }
}