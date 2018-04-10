package shop.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@JsonAutoDetect
@JsonIgnoreProperties(value = {"id", "pass"}, allowGetters = true)
public class Customer {

    private Long id;
    private String ref;
    private String number;
    private String name;
    private String fullname;
    private String email;
    private String pass;
    @JsonDeserialize(as = LinkedList.class, contentAs = Date.class, contentUsing = JsonDateDeserializer.class)
    @JsonSerialize(as = LinkedList.class, contentAs = Date.class, contentUsing = JsonDateSerializer.class)
    private List<Date> payList1;
    @JsonDeserialize(as = LinkedList.class, contentAs = Date.class, contentUsing = JsonDateDeserializer.class)
    @JsonSerialize(as = LinkedList.class, contentAs = Date.class, contentUsing = JsonDateSerializer.class)
    private List<Date> payList2;
    @JsonDeserialize(as = LinkedList.class, contentAs = Date.class, contentUsing = JsonDateDeserializer.class)
    @JsonSerialize(as = LinkedList.class, contentAs = Date.class, contentUsing = JsonDateSerializer.class)
    private List<Date> payList3;
    @JsonDeserialize(as = LinkedList.class, contentAs = Date.class, contentUsing = JsonDateDeserializer.class)
    @JsonSerialize(as = LinkedList.class, contentAs = Date.class, contentUsing = JsonDateSerializer.class)
    private List<Date> payList4;

    {
        ref = "";
        number = "";
        name = "";
        fullname = "";
        email = "";
        pass = "";
    }

    public Customer() {
        this(0L);
    }

    public Customer(Long id) {
        this.id = id;
        payList1 = new LinkedList<>();
        payList2 = new LinkedList<>();
        payList3 = new LinkedList<>();
        payList4 = new LinkedList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<Date> getPayList1() {
        return payList1;
    }

    public void setPayList1(List<Date> payList1) {
        this.payList1 = payList1;
    }

    public List<Date> getPayList2() {
        return payList2;
    }

    public void setPayList2(List<Date> payList2) {
        this.payList2 = payList2;
    }

    public List<Date> getPayList3() {
        return payList3;
    }

    public void setPayList3(List<Date> payList3) {
        this.payList3 = payList3;
    }

    public List<Date> getPayList4() {
        return payList4;
    }

    public void setPayList4(List<Date> payList4) {
        this.payList4 = payList4;
    }

    @Override
    public String toString() {
        return name;
    }
}
