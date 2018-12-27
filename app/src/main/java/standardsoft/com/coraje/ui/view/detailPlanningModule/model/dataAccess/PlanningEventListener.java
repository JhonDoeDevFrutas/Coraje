package standardsoft.com.coraje.ui.view.detailPlanningModule.model.dataAccess;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Planning;

public interface PlanningEventListener {
    void onDataChange(List<Planning> planningList);

    void onError(int resMsg);
}
