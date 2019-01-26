package standardsoft.com.coraje.ui.view.bugsModule.model;

import standardsoft.com.coraje.data.model.entities.Bugs;

public interface BugsInteractor {
    void subscribeToProject();
    void subscribeToCustomer();
    void subscribeToDeveloper();
    void unsubscribeToProject();
    void unsubscribeToCustomer();
    void unsubscribeToDeveloper();

    void addBugs(Bugs bugs);
    void updateBugs(Bugs bugs);
}
