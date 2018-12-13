package standardsoft.com.coraje.data.model.entities;

public class User {// Definicion de la clase padre
    private String name;
    private String movil;

    // Constructor vacio
    public User() {}

    // Constructor completo
    public User(String name, String movil) {
        this.name = name;
        this.movil = movil;
    }

    // Get & Set
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }
}
