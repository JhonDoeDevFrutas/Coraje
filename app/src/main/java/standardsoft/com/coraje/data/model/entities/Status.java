package standardsoft.com.coraje.data.model.entities;

public enum Status {
    proceso("EN PROCESO"), esperando("ESPERANDO REVISION"), listo("LISTO");

    private String description;

    Status(String description) {
        this.description = description;
    }

    public static Status getStatus(String description){
        switch (description){
            case "ESPERANDO REVISION":
                return esperando;
            case "EN PROCESO":
                return proceso;
            case "LISTO":
                return listo;
            default:
                return null;
        }

    }

    // Get && Set
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
