package shop.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Good {
    private Long id;
    private Long owner;
    private Boolean folder;
    private String name;
    private String description;
    private String article;
    private Float price;
    private Float instock;

    {
        owner = new Long(0);
        folder = false;
        name = new String("");
        description = new String("");
        article = new String("");
        price = new Float(0.0);
        instock = new Float(0.0);
    }

    public Good() {
        id = new Long(0);        
    }

    public Good(Long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public Long getOwner() {
        return owner;
    }

    public void setFolder(boolean folder) {
        this.folder = new Boolean(folder);
    }

    public Boolean getFolder() {
        return folder;
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

    public void setArticle(String article) {
        this.article = article;
    }

    public String getArticle() {
        return article;
    }

    public void setPrice(Float price) {
        this.price = (float) new BigDecimal(price).setScale(2, RoundingMode.UP).doubleValue();
    }

    public Float getPrice() {
        return price;
    }

    public void setInstock(Float instock) {
        this.instock = (float) new BigDecimal(instock).setScale(3, RoundingMode.UP).doubleValue();
    }

    public Float getInstock() {
        return instock;
    }

    public String getFilename() {
        StringBuilder sb = new StringBuilder(id.toString()).append(".good");
        return sb.toString();
    }

    @Override
    public String toString() {
        return name;
    }
}