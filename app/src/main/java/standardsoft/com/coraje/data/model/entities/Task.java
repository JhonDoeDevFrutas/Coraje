package standardsoft.com.coraje.data.model.entities;

public class Task { // Definicion de la clase padre

    private String description;//Actividad
    private Developer assignee;//Responsable
    private Status status;     //Estado
    private Priority priority; //Prioridad
    private int estimation;  //Tiempo estimado
    private Customer customer; //Cliente
    private Module module;     //Modulo
    private String task;       //Tarea

    // Constructor vacio
    public Task() {}

    // Constructor completo
    public Task(String description, Developer assignee, Status status, Priority priority, int estimation, Customer customer, Module module, String task) {
        this.description = description;
        this.assignee = assignee;
        this.status = status;
        this.priority = priority;
        this.estimation = estimation;
        this.customer = customer;
        this.module = module;
        this.task = task;
    }

    // Constructor 6 argumentos
    public Task(String description, Status status, Priority priority, Customer customer, Module module, String task) {
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.customer = customer;
        this.module = module;
        this.task = task;
    }

    // Get & Set
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Developer getAssignee() {
        return assignee;
    }

    public void setAssignee(Developer assignee) {
        this.assignee = assignee;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public int getEstimation() {
        return estimation;
    }

    public void setEstimation(int estimation) {
        this.estimation = estimation;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
