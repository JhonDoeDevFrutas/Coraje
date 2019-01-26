package standardsoft.com.coraje.ui.view.detailTaskFragmentModule.model.dataAcces;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.PromptTask;

public interface DetailPromptTaskEventListener {
    void onDataChange(List<PromptTask> promptTaskList);
    void onDataDeveloper(List<Developer> developerList);

    void onError(int resMsg);
}
