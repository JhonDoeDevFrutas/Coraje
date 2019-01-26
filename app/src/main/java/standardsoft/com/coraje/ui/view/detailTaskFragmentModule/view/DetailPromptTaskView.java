package standardsoft.com.coraje.ui.view.detailTaskFragmentModule.view;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.PromptTask;

public interface DetailPromptTaskView {
    void showProgress();
    void hideProgress();

    void requestDatas(List<PromptTask> datas);
    void requestDeveloper(List<Developer> developersDatas);

    void showError(int resMsg);

}
