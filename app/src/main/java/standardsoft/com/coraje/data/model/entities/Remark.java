package standardsoft.com.coraje.data.model.entities;

public class Remark {
    public static final String DESCRIPTION = "description";
    public static final String DATE = "date";

    private String id;
    private String description;
    private long date;          //Fecha creacion
    private String user;        //user comments

    // Constructor vacio
    public Remark() {}

    // Constructor completo
    public Remark(String description, long date) {
        this.description = description;
        this.date = date;
    }

    // Get & Set
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
