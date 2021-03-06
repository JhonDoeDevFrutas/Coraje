package standardsoft.com.coraje.data.model.entities;

import java.util.Comparator;

public class PromptTask extends Task {
    public static final String ID = "id";
    public static final String DESCRIPTION = "description";
    public static final String DATE = "date";
    public static final String ESTIMATION = "estimation";
    public static final String TASK = "task";

    public static final String CUSTOMER = "customer";
    public static final String MODULE = "module";
    public static final String PRIORITY = "priority";
    public static final String ASSIGNEE = "assignee";
    public static final String PROJECT = "project";
    public static final String STATUS = "status";

    private String id;

    // Constructor vacio
    public PromptTask() {
    }

    // Constructor completo
    public PromptTask(String description, Developer assignee, Status status, Priority priority,
                int estimation, Customer customer, Module module, String task, Project project,
                long date, String id) {
        super(description, assignee, status, priority, estimation, customer, module, task, project, date);
        this.id = id;
    }

    // Constructor add
    public PromptTask(String description, Developer assignee, Status status, Priority priority,
                int estimation, Customer customer, Module module, String task, Project project, long date) {
        super(description, assignee, status, priority, estimation, customer, module, task, project, date);
    }

    // Get & Set
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class Comparators{
        public static Comparator<PromptTask> DATE = new Comparator<PromptTask>() {
            @Override
            public int compare(PromptTask promptTask, PromptTask t1) {
                return Long.toString(t1.getDate()).compareTo(Long.toString(promptTask.getDate())) ;
            }
        };
    }

}
