package standardsoft.com.coraje.data.model.entities;

// Cola de errores
public class Bugs extends Task {
    private String id;
    private String effort;      // Esfuerzo
    private String impact;      // Impacto
    private String impactArea;  // Impacto del cliente

    // Get & Set
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEffort() {
        return effort;
    }

    public void setEffort(String effort) {
        this.effort = effort;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getImpactArea() {
        return impactArea;
    }

    public void setImpactArea(String impactArea) {
        this.impactArea = impactArea;
    }
}
