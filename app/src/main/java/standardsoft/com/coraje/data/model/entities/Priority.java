package standardsoft.com.coraje.data.model.entities;

public enum Priority {
    media("Media"), alta("Alta"), baja("baja");

    private String description;

    Priority(String description) {
        this.description = description;
    }

    public static Priority getPriority(String description){
        return Priority.baja;
    }

    // Get && Set
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
