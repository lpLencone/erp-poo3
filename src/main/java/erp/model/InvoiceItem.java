package erp.model;

public class InvoiceItem {
    public int id;
    public int productId;
    public int quantity;
    public double price;

    public InvoiceItem(int id, int productId, int quantity, double price) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
}
