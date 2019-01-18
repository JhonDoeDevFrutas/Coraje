package standardsoft.com.coraje.data.model.entities;

import java.util.Comparator;

//Clientes
public class Customer extends User{
    private String id;

    // Constructor vacio
    public Customer() {}

    public Customer(String name, String movil, String id) {
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
        public static Comparator<Customer> NAME = new Comparator<Customer>() {
            @Override
            public int compare(Customer customer, Customer t1) {
                return customer.getName().compareTo(t1.getName());
            }
        };
    }

}
