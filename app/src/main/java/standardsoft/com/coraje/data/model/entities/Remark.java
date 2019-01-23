package standardsoft.com.coraje.data.model.entities;

import java.util.Comparator;

public class Remark {
    public static final String DESCRIPTION = "description";
    public static final String DATE = "date";

    private String id;
    private String idSubPlanning;
    private String description;
    private long date;                  //Fecha creacion
    private String user;                //user comments
    private SubPlanning subPlanning;    //tarea

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

    public SubPlanning getSubPlanning() {
        return subPlanning;
    }

    public void setSubPlanning(SubPlanning subPlanning) {
        this.subPlanning = subPlanning;
    }

    public String getIdSubPlanning() {
        return idSubPlanning;
    }

    public void setIdSubPlanning(String idSubPlanning) {
        this.idSubPlanning = idSubPlanning;
    }

    public static class Comparators{
        public  static Comparator<Remark> DATE = new Comparator<Remark>() {
            @Override
            public int compare(Remark remark, Remark t1) {
                return Long.toString(t1.getDate()).compareTo(Long.toString(remark.getDate()));
            }
        };
    }
}
