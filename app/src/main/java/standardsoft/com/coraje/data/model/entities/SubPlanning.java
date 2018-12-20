package standardsoft.com.coraje.data.model.entities;

public class SubPlanning extends Task{

    private String id;

    // Constructor vacio
    public SubPlanning() {}

    // Constructor completo
    public SubPlanning(Developer assignee, String task) {
        super(assignee, task);
    }

    // Get & Set
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
