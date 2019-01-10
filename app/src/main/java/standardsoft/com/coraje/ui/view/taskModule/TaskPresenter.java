package standardsoft.com.coraje.ui.view.taskModule;

import standardsoft.com.coraje.data.model.entities.SubPlanning;
import standardsoft.com.coraje.ui.view.taskModule.events.TaskEvent;

public interface TaskPresenter {
    void onCreate();
    void onResume();
    void onPause();
    void onDestroy();

    void update(String idPlanning, SubPlanning subPlanning);

    void onEventListener(TaskEvent event);
}
