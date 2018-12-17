package standardsoft.com.coraje.data.model.entities;

public enum Module {
    historia("HISTORIA CLINICA"), admision("ADMISIONES"), agenda("AGENDA MEDICA"), reporte("REPORTES"),
    parametrizacion("PARAMETRIZACION"), app("APP MOVIL");

    private String description;

    Module(String description) {
        this.description = description;
    }

    public static Module getModule(String description){
        switch (description){
            case "HISTORIA CLINICA":
                return historia;
            case "ADMISIONES":
                return admision;
            case "AGENDA MEDICA":
                return agenda;
            case "PARAMETRIZACION":
                return parametrizacion;
            case "REPORTES":
                return reporte;
            case "APP MOVIL":
                return app;
            default:
                return null;
        }
    }

    // Get & Set
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
