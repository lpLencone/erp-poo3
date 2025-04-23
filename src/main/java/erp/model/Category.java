package erp.model;

public class Category {

    public int id;
    public String name;
    public String description;

    // Construtor
    public Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
