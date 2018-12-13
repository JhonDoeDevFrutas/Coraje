package standardsoft.com.coraje.data.model.entities;

//Clientes
public class Customer extends User{
    private String id;

    public Customer(String name, String movil, String id) {
        super(name, movil);
        this.id = id;
    }

    // Get & Set
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
