package standardsoft.com.coraje.data.model.entities;

public enum Module {
    historia("Historia Clinica"), admision("Admisiones"), agenda("Agenda Medica"), reporte("Reportes"),
    parametrizacion("Parametrización"), app("App Movil");

    private String description;

    Module(String description) {
        this.description = description;
    }

    public static Module getModule(String description){
        return Module.admision;
    }
}
