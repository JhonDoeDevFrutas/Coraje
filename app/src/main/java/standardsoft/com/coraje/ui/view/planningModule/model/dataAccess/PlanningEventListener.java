package standardsoft.com.coraje.ui.view.planningModule.model.dataAccess;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Customer;

public interface PlanningEventListener {
    void onDataChange(List<Customer> customerList);

    void onError(int resMsg);
}
