package standardsoft.com.coraje.ui.view.detailTaskModule.model;

import standardsoft.com.coraje.data.model.entities.Remark;

public interface DetailTaskInteractor {
    void subscribeToRemarkList(String idSubPlanning);
    void addRemarkTask(String idSubPlanning, Remark remark);
}
