package standardsoft.com.coraje.ui.view.SignInModule.view;

import android.content.Intent;

import standardsoft.com.coraje.data.model.entities.User;

public interface SignInView {
    void showProgress();
    void hideProgress();

    void openHomeActivity(User user);

    void onShowError(int resMsg);
}
