package shop.model.impl;

import shop.model.VerificationRequest;

public class VerificationRequestImpl implements VerificationRequest { 

    @Override
    public Boolean verify(String key) {
        return "052b15e78378eb6c82164b63c27be0b1".equals(key);
    }
}