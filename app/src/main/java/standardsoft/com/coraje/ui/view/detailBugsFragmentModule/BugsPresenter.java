package standardsoft.com.coraje.ui.view.detailBugsFragmentModule;

import standardsoft.com.coraje.ui.view.detailBugsFragmentModule.events.DetailBugsEvent;

public interface BugsPresenter {
    void onCreate();
    void onDestroy();
    void onPause();
    void onResume(String phone, boolean filter);

    void onEventListener(DetailBugsEvent event);
}
