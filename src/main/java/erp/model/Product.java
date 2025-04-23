package erp.model;

public class Product {
    public int id;
    public String name;
    public String description;
    public double price;
    public int categoryId;
    public int stock;
    
    public Product(int id, String name, String description, double price, int categoryId, int stock) {
    	this.id = id;
    	this.name = name;
    	this.description = description;
    	this.price = price;
    	this.categoryId = categoryId;
    	this.stock = stock;
    }

    public Product(String name, String description, double price, int categoryId, int stock) {
    	this.name = name;
    	this.description = description;
    	this.price = price;
    	this.categoryId = categoryId;
    	this.stock = stock;
    }
}