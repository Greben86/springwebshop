package shop.entity;

public class Customer {
    private String ref;
    private String number;
    private String name;
    private String pass;
    private Boolean deletionmark;

    public Customer() {
        deletionmark = false;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Boolean getDeletionmark() {
        return deletionmark;
    }

    public void setDeletionmark(Boolean deletionmark) {
        this.deletionmark = deletionmark;
    }

    @Override
    public String toString() {
        return "[" + ref + "] " + name;
    }
}