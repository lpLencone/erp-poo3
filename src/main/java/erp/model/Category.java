package erp.model;

public class Category {

    public int id;
    public String name;

    // Construtor
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Category(String name) {
        this.name = name;
    }
}
