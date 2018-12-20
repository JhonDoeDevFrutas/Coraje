package standardsoft.com.coraje.ui.view.planningModule.events;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Customer;
import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.Project;

public class PlanningEvent {
    public static final int RESULT_PROJECT      = 2;
    public static final int RESULT_DEVELOPER    = 3;
    public static final int SUCCESS             = 0;
    public static final int SUCCESS_ADD         = 1;
    public static final int UPDATE_SUCCESS      = 4;
    public static final int ERROR_SERVER        = 100;

    private List<Project> projects;
    private List<Customer> customers;
    private List<Developer> developers;
    private int typeEvent;
    private int resMsg;

    public PlanningEvent() {}

    // Get & Set
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

    public int getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }

    public int getResMsg() {
        return resMsg;
    }

    public void setResMsg(int resMsg) {
        this.resMsg = resMsg;
    }


}
