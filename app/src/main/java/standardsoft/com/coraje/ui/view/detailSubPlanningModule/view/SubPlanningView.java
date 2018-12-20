package standardsoft.com.coraje.ui.view.detailSubPlanningModule.view;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.SubPlanning;

public interface SubPlanningView {
    void showProgress();
    void hideProgress();

    void requestDeveloper(List<Developer> developersDatas);
    void requestSubPlanning(List<SubPlanning> subPlanningsDatas);

    void addSubPlanning();
    void updateSubPlanning();

    void onShowError(int resMsg);
}
