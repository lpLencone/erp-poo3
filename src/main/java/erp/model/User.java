package erp.model;

public class User {
    public int id;
    public String name;
    public String email;
    public String password;
    public int roleId;

    // Construtor
    public User(String name, String email, String password, int roleId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }
    
    public User() {}
}
