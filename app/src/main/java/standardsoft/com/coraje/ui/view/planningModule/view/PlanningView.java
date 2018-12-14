package standardsoft.com.coraje.ui.view.planningModule.view;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Customer;
import standardsoft.com.coraje.data.model.entities.Planning;

public interface PlanningView {
    void showProgress();
    void hideProgress();

    void addCustomerData(List<Customer> customersDatas);

    void planningAdded();
    void onShowError(int resMsg);
}
