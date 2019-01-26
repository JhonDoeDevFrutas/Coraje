package standardsoft.com.coraje.ui.view.detailTaskFragmentModule;

import standardsoft.com.coraje.ui.view.detailTaskFragmentModule.events.PromptTaskEvent;

public interface DetailPromptTaskPresenter {
    void onCreate();
    void onDestroy();
    void onPause();
    void onResume(String phone, boolean filter);

    void onEventListener(PromptTaskEvent event);
}
