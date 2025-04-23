package erp.model;

import java.util.List;

public class Invoice {
    public int invoiceId;
    public List<CartItem> items;
    public double total;
    public String issuedTo;
    public String date;

    public Invoice(int id, List<CartItem> items, double total, String issuedTo, String date) {
        this.invoiceId = id;
        this.items = items;
        this.total = total;
        this.issuedTo = issuedTo;
        this.date = date;
    }
}