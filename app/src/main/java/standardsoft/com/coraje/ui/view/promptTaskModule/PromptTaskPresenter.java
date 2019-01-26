package standardsoft.com.coraje.ui.view.promptTaskModule;

import standardsoft.com.coraje.data.model.entities.PromptTask;
import standardsoft.com.coraje.ui.view.promptTaskModule.events.PromptTaskEvent;

public interface PromptTaskPresenter {
    void onCreate();
    void onResume();
    void onPause();
    void onDestroy();

    void addPromptTask(PromptTask promptTask);
    void updatePromptTask(PromptTask promptTask);

    void onEventListener(PromptTaskEvent event);
}
