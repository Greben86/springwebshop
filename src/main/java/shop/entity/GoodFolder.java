package shop.entity;

public class GoodFolder {
    private long id;
    private long owner;
    private String name;
    private String description;
    private String filename;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public long getOwner() {
        return owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public String toString() {
        return "GOODFOLDER [" + name + "]";
    }
}