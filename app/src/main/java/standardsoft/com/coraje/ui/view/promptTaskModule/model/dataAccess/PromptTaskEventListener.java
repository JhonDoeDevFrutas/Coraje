package standardsoft.com.coraje.ui.view.promptTaskModule.model.dataAccess;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Customer;
import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.Project;

public interface PromptTaskEventListener {
    void onDataChangeProject(List<Project> projectList);
    void onDataChange(List<Customer> customerList);
    void onDataDeveloper(List<Developer> developerList);

    void onError(int resMsg);
}
