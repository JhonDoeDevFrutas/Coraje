package standardsoft.com.coraje.data.model.entities;

import java.util.Comparator;

public class Project {
    private String id;
    private String description;

    // Constructor vacio
    public Project() {}

    // Constructor completo
    public Project(String id, String description) {
        this.id = id;
        this.description = description;
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

    public static class Comparators{
        public static Comparator<Project> DESCRIPTION = new Comparator<Project>() {
            @Override
            public int compare(Project project, Project t1) {
                return project.description.compareTo(t1.description);
            }
        };
    }
}
