package erp.model;

public class ActivityLog {
	public int userId;
    public String action;
    public java.sql.Timestamp timestamp;
    public String ipAddress;
    public String userAgent;
    
    public ActivityLog(int userId, String action, java.sql.Timestamp timestamp, String ipAddress, String userAgent) {
    	this.userId = userId;
        this.action = action;
        this.timestamp = timestamp;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }
}