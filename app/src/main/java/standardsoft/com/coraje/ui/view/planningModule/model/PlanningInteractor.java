package standardsoft.com.coraje.ui.view.planningModule.model;

import standardsoft.com.coraje.data.model.entities.Planning;

public interface PlanningInteractor {

    void subscribeToProject();
    void subscribeToCustomer();
    void unsubscribeToCustomer();
    void addPlanning(Planning planning);
}
