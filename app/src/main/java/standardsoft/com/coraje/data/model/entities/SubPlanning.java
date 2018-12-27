package standardsoft.com.coraje.data.model.entities;

public class SubPlanning extends Task{

    public static final String ID = "id";
    public static final String TASK = "task";
    public static final String ASSIGNEE = "assignee";
    public static final String STATUS = "status";
    public static final String DATE = "date";
    public static final String ESTIMATION = "estimation";

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
