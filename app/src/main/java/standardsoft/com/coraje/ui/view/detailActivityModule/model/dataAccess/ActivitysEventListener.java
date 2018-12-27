package standardsoft.com.coraje.ui.view.detailActivityModule.model.dataAccess;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.SubPlanning;

public interface ActivitysEventListener {
    void onDataChange(List<SubPlanning> subPlanningList);

    void onError(int resMsg);
}
