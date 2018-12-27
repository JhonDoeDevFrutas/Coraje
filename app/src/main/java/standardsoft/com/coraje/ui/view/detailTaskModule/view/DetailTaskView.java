package standardsoft.com.coraje.ui.view.detailTaskModule.view;

public interface DetailTaskView {
    void showProgress();
    void hideProgress();

    void addRemarkTask();

    void onShowError(int resMsg);
}
