package standardsoft.com.coraje.data.model.entities;

public enum Priority {
    media("Media"), alta("Alta"), baja("baja");

    private String description;

    Priority(String description) {
        this.description = description;
    }

    public static Priority getPriority(String description){
        switch (description){
            case "Media":
                return media;
            case "Alta":
                return alta;
            default:
                return baja;
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
