package standardsoft.com.coraje.data.model.entities;

public class Developer extends User {
    private String id;

    public Developer(String name, String movil, String id) {
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
