package standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.view;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Remark;

public interface RemarkView {
    void showProgress();
    void hideProgress();

    void resultSubPlanning(List<Remark> datas);

    void showError(int resMsg);
}
