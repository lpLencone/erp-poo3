package erp.model;

public class Receipt {
    public int id;
    public int invoiceId;
    public double totalPaid;
    public String date;

    public Receipt(int id, int invoiceId, double totalPaid, String date) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.totalPaid = totalPaid;
        this.date = date;
    }
}