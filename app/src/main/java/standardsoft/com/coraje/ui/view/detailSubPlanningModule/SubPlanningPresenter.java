package standardsoft.com.coraje.ui.view.detailSubPlanningModule;

import standardsoft.com.coraje.data.model.entities.SubPlanning;
import standardsoft.com.coraje.ui.view.detailSubPlanningModule.events.SubPlanningEvent;

public interface SubPlanningPresenter {
    void onCreate();
    void onResume(String idPlanning);
    void onPause();
    void onDestroy();

    void addSubPlanning(String idPlanning, SubPlanning subPlanning);

    void onEventListener(SubPlanningEvent event);
}
