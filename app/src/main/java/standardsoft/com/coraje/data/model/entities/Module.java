package standardsoft.com.coraje.data.model.entities;

public enum Module {
    historia("Historia Clinica"), admision("Admisiones"), agenda("Agenda Medica"), reporte("Reportes"),
    parametrizacion("Parametrización"), app("App Movil");

    private String description;

    Module(String description) {
        this.description = description;
    }

    public static Module getModule(String description){
        switch (description){
            case "Historia Clinica":
                return historia;
            case "Admisiones":
                return admision;
            case "Agenda Medica":
                return agenda;
            case "Parametrización":
                return parametrizacion;
            case "Reportes":
                return reporte;
            default:
                return app;
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
