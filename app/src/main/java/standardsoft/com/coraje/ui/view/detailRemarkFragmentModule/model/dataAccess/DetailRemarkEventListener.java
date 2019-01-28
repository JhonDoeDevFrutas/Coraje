package standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.model.dataAccess;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Bugs;
import standardsoft.com.coraje.data.model.entities.PromptTask;
import standardsoft.com.coraje.data.model.entities.Remark;
import standardsoft.com.coraje.data.model.entities.SubPlanning;

public interface DetailRemarkEventListener {
    void onDataChange(List<Remark> remarkList);
    void onDataSubPlanning(List<SubPlanning> subPlanningList);
    void onDataBugs(List<Bugs> bugsList);
    void onDataPrompt(List<PromptTask> promptTaskList);

    void onError(int resMsg);
}
