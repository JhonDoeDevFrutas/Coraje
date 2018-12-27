package standardsoft.com.coraje.common;

public interface BasicErrorEventCallback {
    void onSuccess();
    void onError(int typeEvent, int resMsg);
    void onError();
}
