package erp.model;

public class InvoiceItem {
    public int id;
    public int productId;
    public int quantity;

    public InvoiceItem(int id, int productId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }
}
