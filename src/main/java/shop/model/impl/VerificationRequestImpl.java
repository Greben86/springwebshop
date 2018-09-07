package shop.model.impl;

import shop.model.VerificationRequest;

public class VerificationRequestImpl implements VerificationRequest { 
    
    private final String key;

    private VerificationRequestImpl() {
        key = new String();
    }

    public VerificationRequestImpl(String key) {
        this.key = key;
    }

    @Override
    public Boolean verify(String key) {
        return this.key.equals(key);
    }
}