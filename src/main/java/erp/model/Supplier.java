package erp.model;

public class Supplier {

    public int id;
    public String name;

    public Supplier(int id, String name) {
        this.name = name;
    }

    public Supplier(String name) {
        this.name = name;
    }
}
