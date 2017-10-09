package shop.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Good {
    private long id;
    private long owner;
    private String ref;
    private String name;
    private String description;
    private String articul;
    private String dimension;
    private String filename;
    private Float price;
    private Boolean exist;
    private Boolean deletionmark;

    public Good() {
        deletionmark = false;
    }

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

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
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

    public void setArticul(String articul) {
        this.articul = articul;
    }

    public String getArticul() {
        return articul;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getDimension() {
        return dimension;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setPrice(Float price) {
        this.price = (float) new BigDecimal(price).setScale(2, RoundingMode.UP).doubleValue();
    }

    public Float getPrice() {
        return price;
    }

    public void setExist(Boolean exist) {
        this.exist = exist;
    }

    public Boolean getExist() {
        return exist;
    }

    public Boolean getDeletionmark() {
        return deletionmark;
    }

    public void setDeletionmark(Boolean deletionmark) {
        this.deletionmark = deletionmark;
    }

    @Override
    public String toString() {
        return "GOOD [" + name + "]";
    }
}