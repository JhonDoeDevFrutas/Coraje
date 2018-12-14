package standardsoft.com.coraje.data.model.entities;


//Junta de Planificación
public class Planning extends Task{
    private String id;
    private int percentage; // Porcenta de desarrollo

    public Planning(String description, Status status, Priority priority, Customer customer, Module module, String task) {
        super(description, status, priority, customer, module, task);
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
