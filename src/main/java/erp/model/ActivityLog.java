package erp.model;

public class ActivityLog {
    public String action;
    public java.sql.Timestamp timestamp;
    public String ipAddress;
    public String userAgent;

    public ActivityLog(String action, java.sql.Timestamp timestamp, String ipAddress, String userAgent) {
        this.action = action;
        this.timestamp = timestamp;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }
}