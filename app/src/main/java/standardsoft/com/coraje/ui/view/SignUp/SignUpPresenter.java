package standardsoft.com.coraje.ui.view.SignUp;

import standardsoft.com.coraje.data.model.entities.User;
import standardsoft.com.coraje.ui.view.SignUp.events.SignUpEvent;

public interface SignUpPresenter {
    void onCreate();
    void onPause();
    void onDestroy();

    void addUser(User user);

    void onEventListener(SignUpEvent event);
}
