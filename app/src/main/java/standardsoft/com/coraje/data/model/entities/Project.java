package standardsoft.com.coraje.data.model.entities;

public class Project {
    private String id;
    private String description;

    // Constructor vacio
    public Project() {}

    // Constructor completo
    public Project(String id, String description) {
        this.id = id;
        this.description = description;
    }

    // Get & Set
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
