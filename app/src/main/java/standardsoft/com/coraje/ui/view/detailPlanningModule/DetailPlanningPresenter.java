package standardsoft.com.coraje.ui.view.detailPlanningModule;

import standardsoft.com.coraje.ui.view.detailPlanningModule.events.DetailPlanningEvent;

public interface DetailPlanningPresenter {
    void onCreate();
    void onDestroy();
    void onPause();
    void onResume();

    void onEventListener(DetailPlanningEvent event);
}
