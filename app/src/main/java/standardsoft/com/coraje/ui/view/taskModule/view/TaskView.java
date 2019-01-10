package standardsoft.com.coraje.ui.view.taskModule.view;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Developer;

public interface TaskView {
    void showProgress();
    void hideProgress();

    void resultDeveloper(List<Developer> developersDatas);

    void updateSuccess();
}
