package standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.view;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Bugs;
import standardsoft.com.coraje.data.model.entities.PromptTask;
import standardsoft.com.coraje.data.model.entities.Remark;
import standardsoft.com.coraje.data.model.entities.SubPlanning;

public interface RemarkView {
    void showProgress();
    void hideProgress();

    void resultRemark(List<Remark> datas);
    void resultSubPlanning(List<SubPlanning> datas);
    void resultBugs(List<Bugs> datas);
    void resultPrompt(List<PromptTask> datas);

    void showError(int resMsg);
}
