package standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.model.dataAccess;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Remark;
import standardsoft.com.coraje.data.model.entities.SubPlanning;

public interface DetailRemarkEventListener {
    void onDataChange(List<Remark> remarkList);
    void onDataSubPlanning(List<SubPlanning> subPlanningList);

    void onError(int resMsg);
}
