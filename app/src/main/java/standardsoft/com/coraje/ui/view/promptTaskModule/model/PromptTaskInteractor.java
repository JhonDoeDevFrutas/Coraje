package standardsoft.com.coraje.ui.view.promptTaskModule.model;

import standardsoft.com.coraje.data.model.entities.PromptTask;

public interface PromptTaskInteractor {
    void subscribeToProject();
    void subscribeToCustomer();
    void subscribeToDeveloper();
    void unsubscribeToProject();
    void unsubscribeToCustomer();
    void unsubscribeToDeveloper();

    void addPromptTask(PromptTask promptTask);
    void updatePromptTask(PromptTask promptTask);
}
