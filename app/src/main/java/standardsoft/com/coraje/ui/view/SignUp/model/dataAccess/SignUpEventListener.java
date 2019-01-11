package standardsoft.com.coraje.ui.view.SignUp.model.dataAccess;

public interface SignUpEventListener {
    void onSuccess();
    void onError();
    void onError(int resMsg);
}
