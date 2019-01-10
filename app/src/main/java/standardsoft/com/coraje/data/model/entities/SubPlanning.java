package standardsoft.com.coraje.data.model.entities;

import android.support.annotation.NonNull;

import java.util.Comparator;

public class SubPlanning extends Task implements Comparable<SubPlanning>{

    public static final String ID = "id";
    public static final String ID_PLANNING = "idPlanning";
    public static final String TASK = "task";
    public static final String ASSIGNEE = "assignee";
    public static final String STATUS = "status";
    public static final String DATE = "date";
    public static final String ESTIMATION = "estimation";
    public static final String DESCRIPTION = "description";

    private String id;
    private String idPlanning;

    // Constructor vacio
    public SubPlanning() {}

    // Constructor completo
    public SubPlanning(Developer assignee, String task) {
        super(assignee, task);
    }

    // Get & Set
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPlanning() {
        return idPlanning;
    }

    public void setIdPlanning(String idPlanning) {
        this.idPlanning = idPlanning;
    }

    @Override
    public int compareTo(@NonNull SubPlanning subPlanning) {
        return 0;
    }

    public static class Comparators{
        public static Comparator<SubPlanning> TASK = new Comparator<SubPlanning>() {
            @Override
            public int compare(SubPlanning subPlanning, SubPlanning t1) {
                return subPlanning.getTask().toString().compareTo(t1.getTask().toString());
            }
        };

        public static Comparator<SubPlanning> DATE = new Comparator<SubPlanning>() {
            @Override
            public int compare(SubPlanning subPlanning, SubPlanning t1) {
                return Long.toString(t1.getDate()).compareTo(Long.toString(subPlanning.getDate())) ;
            }
        };
    }
}
