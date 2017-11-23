package shop.entity;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.AfterClass;

public class GoodTest {
    private final Long id = 123L;
    private final Long owner = 12L;
    private final Boolean folder = false;
    private final String name = "Name";
    private final String description = "Description";
    private final String article = "Article";
    private final Float price = 10.12F;
    private final Float instock = 15.543F;

    private Good createGood() {
        final Good good = new Good();
        good.setId(id);
        good.setOwner(owner);
        good.setFolder(folder);
        good.setName(name);
        good.setDescription(description);
        good.setArticle(article);
        good.setPrice(price);
        good.setInstock(instock);
        return good;
    }  
    
    @BeforeClass
    public static void before() {
		System.out.println("Test Good started..");
	} 
	
    @AfterClass 
    public static void after() {
		System.out.println("..test Good finished.");
	}

    @Test
    public void getId() throws Exception {
        final Good good = createGood();
        Long id2 = good.getId();
        assertEquals("getId is fail", id, id2);
    }

    @Test
    public void setId() throws Exception {
        final Good good = createGood();
        Long id1 = 555L;
        good.setId(id1);
        Long id2 = good.getId();
        assertEquals("setId is fail", id1, id2);
    }

    @Test
    public void getOwner() throws Exception {
        final Good good = createGood();
        Long owner2 = good.getOwner();
        assertEquals("getOwner is fail", owner, owner2);
    }

    @Test
    public void setOwner() throws Exception {
        final Good good = createGood();
        Long owner1 = 555L;
        good.setOwner(owner1);
        Long owner2 = good.getOwner();
        assertEquals("setOwner is fail", owner1, owner2);
    }

    @Test
    public void getFolder() throws Exception {
        final Good good = createGood();
        Boolean folder2 = good.getFolder();
        assertEquals("getFolder is fail", folder, folder2);
    }

    @Test
    public void setFolder() throws Exception {
        final Good good = createGood();
        Boolean folder1 = true;
        good.setFolder(folder1);
        Boolean folder2 = good.getFolder();
        assertEquals("setFolder is fail", folder1, folder2);
    }

    @Test
    public void getName() throws Exception {
        final Good good = createGood();
        String name2 = good.getName();
        assertEquals("getName is fail", name, name2);
    }

    @Test
    public void setName() throws Exception {
        final Good good = createGood();
        String name1 = "testName";
        good.setName(name1);
        String name2 = good.getName();
        assertEquals("setName is fail", name1, name2);
    }

    @Test
    public void getDescription() throws Exception {
        final Good good = createGood();
        String description2 = good.getDescription();
        assertEquals("getDescription is fail", description, description2);
    }

    @Test
    public void setDescription() throws Exception {
        final Good good = createGood();
        String description1 = "testDescription";
        good.setDescription(description1);
        String description2 = good.getDescription();
        assertEquals("setDescription is fail", description1, description2);
    }

    @Test
    public void getArticle() throws Exception {
        final Good good = createGood();
        String article2 = good.getArticle();
        assertEquals("getArticle is fail", article, article2);
    }

    @Test
    public void setArticle() throws Exception {
        final Good good = createGood();
        String article1 = "testArticle";
        good.setArticle(article1);
        String article2 = good.getArticle();
        assertEquals("setArticle is fail", article1, article2);
    }

    @Test
    public void getPrice() throws Exception {
        final Good good = createGood();
        Float price2 = good.getPrice();
        assertEquals("getPrice is fail", price, price2);
    }

    @Test
    public void setPrice() throws Exception {
        final Good good = createGood();
        Float price1 = 5.32F;
        good.setPrice(price1);
        Float price2 = good.getPrice();
        assertEquals("setPrice is fail", price1, price2);
    }

    @Test
    public void getInstock() throws Exception {
        final Good good = createGood();
        Float instock2 = good.getInstock();
        assertEquals("getInstock is fail", instock, instock2);
    }

    @Test
    public void setInstock() throws Exception {
        final Good good = createGood();
        Float instock1 = 4.1F;
        good.setInstock(instock1);
        Float instock2 = good.getInstock();
        assertEquals("setInstock is fail", instock1, instock2);
    }
    
    @Test
    public void getFilename() throws Exception {
        final Good good = createGood();
        Long id1 = 555L;
        good.setId(id1);
        String filename = good.getFilename();
        assertEquals("getFilename is fail", "555.good", filename);
    }
}