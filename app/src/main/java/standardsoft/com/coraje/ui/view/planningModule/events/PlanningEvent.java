package standardsoft.com.coraje.ui.view.planningModule.events;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Customer;

public class PlanningEvent {
    public static final int SUCCESS     = 0;
    public static final int ERROR_SERVER = 100;

    private List<Customer> customers;
    private int typeEvent;
    private int resMsg;

    public PlanningEvent() {}

    // Get & Set

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
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
