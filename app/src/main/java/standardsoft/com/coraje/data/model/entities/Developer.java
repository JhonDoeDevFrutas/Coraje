package standardsoft.com.coraje.data.model.entities;

import java.util.Comparator;

public class Developer extends User {
    private String id;

    public Developer() {
    }

    public Developer(String name, String movil, String id) {
        super(name, movil);
        this.id = id;
    }

    // Get & Set
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class Comparators{
        public static Comparator<Developer> name = new Comparator<Developer>() {
            @Override
            public int compare(Developer developer, Developer t1) {
                return developer.getName().compareTo(t1.getName());
            }
        };
    }
}
