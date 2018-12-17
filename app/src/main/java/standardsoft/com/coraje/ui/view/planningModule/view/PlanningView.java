package standardsoft.com.coraje.ui.view.planningModule.view;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Customer;
import standardsoft.com.coraje.data.model.entities.Planning;
import standardsoft.com.coraje.data.model.entities.Project;

public interface PlanningView {
    void showProgress();
    void hideProgress();

    void resultCustomer(List<Customer> customersDatas);
    void resultProject(List<Project> projects);
    void planningAdded();

    void onShowError(int resMsg);
}
