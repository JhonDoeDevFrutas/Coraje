package standardsoft.com.coraje.data.model.entities;

public class User {// Definicion de la clase padre
    public static final String NAME  = "name";
    public static final String PHONE = "phone";

    private String name;
    private String movil;
    private String password;

    // Constructor vacio
    public User() {}

    // Constructor completo
    public User(String name, String movil) {
        this.name = name;
        this.movil = movil;
    }

    // Constructor 3 argumentos
    public User(String name, String movil, String password) {
        this.name = name;
        this.movil = movil;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
