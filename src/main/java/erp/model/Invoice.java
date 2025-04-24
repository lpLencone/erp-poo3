package erp.model;

public class Invoice {
	public int id;
    public int userId;
    public double total;
    public String date;

    public Invoice(int userId, double total, String date) {
        this.userId = userId;
        this.total = total;
        this.date = date;
    }
}
