package standardsoft.com.coraje.ui.view.SignInModule.model.dataAccess;

import standardsoft.com.coraje.data.model.entities.User;

public interface SignInEventListener {
    void onSuccess(User user);
    void onError();

}
