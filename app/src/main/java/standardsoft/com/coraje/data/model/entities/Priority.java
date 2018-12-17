package standardsoft.com.coraje.data.model.entities;

public enum Priority {
    media("MEDIA"), alta("ALTA"), baja("BAJA");

    private String description;

    Priority(String description) {
        this.description = description;
    }

    public static Priority getPriority(String description){
        switch (description){
            case "MEDIA":
                return media;
            case "ALTA":
                return alta;
            case "BAJA":
                return baja;
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
