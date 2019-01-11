package standardsoft.com.coraje.ui.view.SignUp.view;

public interface SignUpView {
    void showProgress();
    void hideProgress();

    void addUser();

    void onShowError(int resMsg);
}
