package standardsoft.com.coraje.data.model.entities;

public enum Module {
    admision("ADMISIONES"), agenda("AGENDA MEDICA"), app("APP MOVIL"), factura("FACTURACIÓN"),
    historia("HISTORIA CLINICA"), laboratorio("LABORATORIO"), parametrizacion("PARAMETRIZACION"),
    reporte("REPORTES"), seguridad("SEGURIDAD"), honorario("HONORARIO");

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
            case "FACTURACIÓN":
                return factura;
            case "LABORATORIO":
                return laboratorio;
            case "SEGURIDAD":
                return seguridad;
            case "HONORARIO":
                return honorario;
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
