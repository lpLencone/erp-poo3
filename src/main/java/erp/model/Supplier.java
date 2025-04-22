package erp.model;

public class Supplier {

    private int id;
    private String name;
    private String contactInfo;

    // Construtor
    public Supplier() {}

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    // Método toString() para exibir os dados do fornecedor
    @Override
    public String toString() {
        return "Supplier [id=" + id + ", name=" + name + ", contactInfo=" + contactInfo + "]";
    }
}
