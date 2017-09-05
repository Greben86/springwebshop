package shop.model.impl;

import shop.model.VerificationRequest;

public class VerificationRequestImpl implements VerificationRequest { 
    private String key;

    private VerificationRequestImpl() {}

    public VerificationRequestImpl(String key) {
        this.key = new String(key);
    }

    @Override
    public Boolean verify(String key) {
        return this.key.equals(key);
    }
}