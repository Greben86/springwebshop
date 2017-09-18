package shop.entity;

public class Good {
    private String ref;
    private String name;
    private String description;
    private String articul;
    private String dimension;
    private Float price;
    private Boolean exist;

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

    public void setPrice(Float price) {
        this.price = price;
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

    @Override
    public String toString() {
        return "[" + ref + "] " + name;
    }
}