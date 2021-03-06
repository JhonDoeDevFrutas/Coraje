package standardsoft.com.coraje.ui.view.planningModule.view;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Customer;
import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.Project;

public interface PlanningView {
    void showProgress();
    void hideProgress();

    void resultCustomer(List<Customer> customersDatas);
    void resultProject(List<Project> projectsDatas);
    void resultDeveloper(List<Developer> developersDatas);

    void planningAdded();
    void updateSuccess();

    void onShowError(int resMsg);
}
