package standardsoft.com.coraje.ui.view.planningModule;

import standardsoft.com.coraje.data.model.entities.Planning;
import standardsoft.com.coraje.ui.view.planningModule.events.PlanningEvent;

public interface PlanningPresenter {
    void onCreate();
    void onResume();
    void onPause();
    void onDestroy();

    void addPlanning(Planning planning);

    void onEventListener(PlanningEvent event);
}
