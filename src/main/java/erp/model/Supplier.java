package erp.model;

public class Supplier {

    public int id;
    public String name;
    public String contactInfo;

    public Supplier(int id, String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public Supplier(String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
    }
}
