package standardsoft.com.coraje.ui.view.detailTaskModule.view;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Remark;

public interface DetailTaskView {
    void clearUIElements();
    void enableUIElements();
    void disableUIElements();
    void showProgress();
    void hideProgress();

    void resultRemark(List<Remark> datas);
    void addRemarkTask();

    void onShowError(int resMsg);
}
