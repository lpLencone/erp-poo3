package erp.model;

public class Role {
    public int id;
    public String name;
    
    public Role(String name) {
        this.name = name;
    }
    
    public Role(int id, String name) {
    	this.id = id;
        this.name = name;
    }
}
