package standardsoft.com.coraje.ui.view.planningModule.model;

import standardsoft.com.coraje.data.model.entities.Planning;

public interface PlanningInteractor {
    void onResume();
    void onPause();

    void addPlanning(Planning planning);
}
