package standardsoft.com.coraje.ui.view.taskModule.model;

import standardsoft.com.coraje.data.model.entities.SubPlanning;

public interface TaskInteractor {
    void subscribeToDeveloper();
    void unsubscribeToDeveloper();

    void update(String idPlanning, SubPlanning subPlanning);
}
