package standardsoft.com.coraje.data.model.entities;

public enum Status {
    esperando("Esperando revisión"), proceso("En proceso"), listo("Listo");

    private String description;

    Status(String description) {
        this.description = description;
    }

    public static Status getStatus(String description){
        return Status.esperando;
    }

    // Get && Set
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
