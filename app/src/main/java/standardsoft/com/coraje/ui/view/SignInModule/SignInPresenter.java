package standardsoft.com.coraje.ui.view.SignInModule;

import standardsoft.com.coraje.ui.view.SignInModule.events.SignInEvent;

public interface SignInPresenter {
    void onCreate();
    void onPause();
    void onDestroy();

    void subscribeToUser(String phone, String password);

    void onEventListener(SignInEvent event);
}
