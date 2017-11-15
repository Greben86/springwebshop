package shop.model;

public interface VerificationRequest {
    /**
     * Verify API key
     * @param key - API-key 
     */
    Boolean verify(String key);
}