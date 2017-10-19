package shop.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Good {
    private long id;
    private long owner;
    private Boolean folder;
    private String name;
    private String description;
    private String article;
    private Float price;
    private Float instock;

    public Good() {
        id = 0;
        owner = 0;
        folder = false;
        name = new String("");
        description = new String("");
        article = new String("");
        price = new Float(0.0);
        instock = new Float(0.0);
    }

    public Good(ResultSet set) throws SQLException {
        setId(set.getLong("id"));
        setOwner(set.getLong("owner"));
        setFolder(set.getString("folder").equals("T") ? true : false);
        setName(set.getString("name"));
        setDescription(set.getString("description"));
        setArticle(set.getString("article"));
        setPrice(set.getFloat("price"));
        setInstock(set.getFloat("instock"));
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

    @Override
    public String toString() {
        return "GOOD [" + name + "]";
    }
}