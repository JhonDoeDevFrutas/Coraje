package standardsoft.com.coraje.data.model.entities;


//Junta de Planificación
public class Planning extends Task{
    private String id;
    private short percentage; // Porcenta de desarrollo

    public Planning(String description, Status status, Priority priority, Customer customer, Module module, String task, String id, short percentage) {
        super(description, status, priority, customer, module, task);
        this.id = id;
        this.percentage = percentage;
    }

    // Get & Set
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public short getPercentage() {
        return percentage;
    }

    public void setPercentage(short percentage) {
        this.percentage = percentage;
    }
}
