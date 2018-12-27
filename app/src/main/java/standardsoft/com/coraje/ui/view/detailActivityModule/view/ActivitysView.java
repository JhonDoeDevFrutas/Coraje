package standardsoft.com.coraje.ui.view.detailActivityModule.view;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.SubPlanning;

public interface ActivitysView {

    void showProgress();
    void hideProgress();

    void resultSubPlanning(List<SubPlanning> datas);

    void showError(int resMsg);
}
