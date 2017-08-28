package shop.model;

public interface CustomerAuth {
    Boolean checkPass(String number, String pass);
}