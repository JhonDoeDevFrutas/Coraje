package standardsoft.com.coraje.ui.view.planningModule.view;

public interface PlanningView {
    void showProgress();
    void hideProgress();

    void planningAdded();
    void onShowError(int resMsg);
}
