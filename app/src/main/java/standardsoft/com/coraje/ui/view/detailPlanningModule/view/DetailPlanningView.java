package standardsoft.com.coraje.ui.view.detailPlanningModule.view;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Planning;

public interface DetailPlanningView {
    void showProgress();
    void hideProgress();

    void addDatas(List<Planning> datas);

    void showError(int resMsg);
}
