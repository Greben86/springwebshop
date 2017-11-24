package shop.entity;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.AfterClass;

public class CustomerTest {
    private final Long id = 123L;
    private final String ref = "E07ABA8D-5FB6-445D-A649-F9FAC24121AC";
    private final String number = "Number";
    private final String name = "Name";
    private final String fullname = "Fullname";
    private final String email = "Email";
    private final String pass = "Pass";

    private Customer createCustomer() {
        final Customer customer = new Customer();
        customer.setId(id);
        customer.setRef(ref);
        customer.setNumber(number);
        customer.setName(name);
        customer.setFullname(fullname);
        customer.setEmail(email);
        customer.setPass(pass);
        return customer;
    }

    @BeforeClass 
    public static void before() {
		System.out.println("Test Customer started..");
	} 
	
    @AfterClass 
    public static void after() {
		System.out.println("..test Customer finished.");
    }
    
    @Test
    public void getId() throws Exception {
        final Customer customer = createCustomer();
        Long id2 = customer.getId();
        assertEquals("Customer.getId is fail", id, id2);
    }

    @Test
    public void setId() throws Exception {
        final Customer customer = createCustomer();
        Long id1 = 555L;
        customer.setId(id1);
        Long id2 = customer.getId();
        assertEquals("Customer.setId is fail", id1, id2);
    }

    @Test
    public void getRef() throws Exception {
        final Customer customer = createCustomer();
        String ref2 = customer.getRef();
        assertEquals("Customer.getRef is fail", ref, ref2);
    }

    @Test
    public void setRef() throws Exception {
        final Customer customer = createCustomer();
        String ref1 = "26FB88BA-4E09-4670-A25E-684464F5C40A";
        customer.setRef(ref1);
        String ref2 = customer.getRef();
        assertEquals("Customer.setRef is fail", ref1, ref2);
    }

    @Test
    public void getNumber() throws Exception {
        final Customer customer = createCustomer();
        String number2 = customer.getNumber();
        assertEquals("Customer.getNumber is fail", number, number2);
    }

    @Test
    public void setNumber() throws Exception {
        final Customer customer = createCustomer();
        String number1 = "testNumber";
        customer.setNumber(number1);
        String number2 = customer.getNumber();
        assertEquals("Customer.setNumber is fail", number1, number2);
    }

    @Test
    public void getName() throws Exception {
        final Customer customer = createCustomer();
        String name2 = customer.getName();
        assertEquals("Customer.getName is fail", name, name2);
    }

    @Test
    public void setName() throws Exception {
        final Customer customer = createCustomer();
        String name1 = "testName";
        customer.setName(name1);
        String name2 = customer.getName();
        assertEquals("Customer.setName is fail", name1, name2);
    }

    @Test
    public void getFullname() throws Exception {
        final Customer customer = createCustomer();
        String fullname2 = customer.getFullname();
        assertEquals("Customer.getFullname is fail", fullname, fullname2);
    }

    @Test
    public void setFullname() throws Exception {
        final Customer customer = createCustomer();
        String fullname1 = "testFullname";
        customer.setFullname(fullname1);
        String fullname2 = customer.getFullname();
        assertEquals("Customer.setFullname is fail", fullname1, fullname2);
    }

    @Test
    public void getEmail() throws Exception {
        final Customer customer = createCustomer();
        String email2 = customer.getEmail();
        assertEquals("Customer.getEmail is fail", email, email2);
    }

    @Test
    public void setEmail() throws Exception {
        final Customer customer = createCustomer();
        String email1 = "testEmail";
        customer.setEmail(email1);
        String email2 = customer.getEmail();
        assertEquals("Customer.setEmail is fail", email1, email2);
    }

    @Test
    public void getPass() throws Exception {
        final Customer customer = createCustomer();
        String pass2 = customer.getPass();
        assertEquals("Customer.getPass is fail", pass, pass2);
    }

    public void setPass(String pass) {
        final Customer customer = createCustomer();
        String pass1 = "testPass";
        customer.setPass(pass1);
        String pass2 = customer.getPass();
        assertEquals("Customer.setPass is fail", pass1, pass2);
    }
}