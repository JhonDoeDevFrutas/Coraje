package standardsoft.com.coraje.ui.view.detailSubPlanningModule.model.dataAcces;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.SubPlanning;

public interface SubPlanningEventListener {
    void onDataSubPlanning(List<SubPlanning> subPlanningList);
    void onDataDeveloper(List<Developer> developerList);

    void onError(int resMsg);
}
