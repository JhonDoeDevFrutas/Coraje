package standardsoft.com.coraje.ui.view.detailTaskModule;

import standardsoft.com.coraje.data.model.entities.Remark;
import standardsoft.com.coraje.ui.view.detailTaskModule.events.DetailTaskEvent;

public interface DetailTaskPresenter {
    void onCreate();
    void onResume(String idSubPlanning);
    void onPause();
    void onDestroy();

    void addRemarkTask(String idPlanning, String idSubPlanning, Remark remark);

    void onEventListener(DetailTaskEvent event);
}
