package standardsoft.com.coraje.ui.view.planningModule.model;

import standardsoft.com.coraje.data.model.entities.Planning;

public interface PlanningInteractor {

    void subscribeToProject();
    void subscribeToCustomer();
    void subscribeToDeveloper();
    void unsubscribeToProject();
    void unsubscribeToCustomer();
    void unsubscribeToDeveloper();

    void addPlanning(Planning planning);
    void updatePlanning(Planning planning);
}
