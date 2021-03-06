package standardsoft.com.coraje.ui.view.promptTaskModule.view;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Customer;
import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.Project;

public interface PromptView {
    void showProgress();
    void hideProgress();

    void resultCustomer(List<Customer> customersDatas);
    void resultProject(List<Project> projectsDatas);
    void resultDeveloper(List<Developer> developersDatas);

    void planningAdded();
    void updateSuccess();

    void onShowError(int resMsg);

}
