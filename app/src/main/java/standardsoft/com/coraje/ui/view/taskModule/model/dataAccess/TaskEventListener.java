package standardsoft.com.coraje.ui.view.taskModule.model.dataAccess;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Developer;

public interface TaskEventListener {
    void onDataDeveloper(List<Developer> developerList);

    void onError(int resMsg);
}
