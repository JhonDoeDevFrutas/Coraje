package standardsoft.com.coraje.ui.view.detailTaskModule;

import standardsoft.com.coraje.data.model.entities.Remark;
import standardsoft.com.coraje.ui.view.detailTaskModule.events.DetailTaskEvent;

public interface DetailTaskPresenter {
    void onCreate();
    void addRemarkTask(String idSubPlanning, Remark remark);

    void onEventListener(DetailTaskEvent event);
}
