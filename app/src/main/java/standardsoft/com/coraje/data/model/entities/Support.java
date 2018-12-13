package standardsoft.com.coraje.data.model.entities;

// Soporte a clientes
public class Support extends Task {
    private String id;
    private byte confidence;    // Valoracion

    // Get & Set
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte getConfidence() {
        return confidence;
    }

    public void setConfidence(byte confidence) {
        this.confidence = confidence;
    }
}
