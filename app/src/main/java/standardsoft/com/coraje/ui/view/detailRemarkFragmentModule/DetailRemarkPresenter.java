package standardsoft.com.coraje.ui.view.detailRemarkFragmentModule;

import standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.events.RemarkEvent;

public interface DetailRemarkPresenter {
    void onCreate();
    void onDestroy();
    void onPause();
    void onResume();

    void onEventListener(RemarkEvent event);
}
