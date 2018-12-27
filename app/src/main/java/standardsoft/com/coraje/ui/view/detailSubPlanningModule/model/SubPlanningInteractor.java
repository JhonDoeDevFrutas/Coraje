package standardsoft.com.coraje.ui.view.detailSubPlanningModule.model;

import standardsoft.com.coraje.data.model.entities.Planning;
import standardsoft.com.coraje.data.model.entities.SubPlanning;

public interface SubPlanningInteractor {
    void subscribeToDeveloperList();
    void subscribeToSubPlanningList(String idPlanning);

    void addSubPlanning(String idPlanning, SubPlanning subPlanning);
    void updateSubPlanning(SubPlanning subPlanning);
}
