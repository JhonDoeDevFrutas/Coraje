package standardsoft.com.coraje.ui.view.detailActivityModule;

import standardsoft.com.coraje.ui.view.detailActivityModule.events.ActivitysEvent;

public interface ActivitysPresenter {
    void onCreate();
    void onDestroy();
    void onPause();
    void onResume(String phone, boolean selectAll);

    void onEventListener(ActivitysEvent event);
}
