package standardsoft.com.coraje.data.model.entities;


//Junta de Planificación
public class Planning extends Task{

    public static final String ID = "id";
    public static final String DESCRIPTION = "description";
    public static final String DATE = "date";
    public static final String ESTIMATION = "estimation";
    public static final String PERCENTAGE = "estimation";
    public static final String TASK = "task";

    public static final String CUSTOMER = "customer";
    public static final String MODULE = "module";
    public static final String PRIORITY = "priority";
    public static final String ASSIGNEE = "assignee";
    public static final String PROJECT = "project";
    public static final String STATUS = "status";

    private String id;
    private int percentage; // Porcenta de desarrollo

    public Planning() {}

    // Constructor completo
    public Planning(String description, Status status, Priority priority, Customer customer,
                    Module module, String task, Project project,Developer assignee, long date) {
        super(description, status, priority, customer, module, task, project, assignee, date);
        this.percentage = 0;
    }

    // Get & Set
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

}
