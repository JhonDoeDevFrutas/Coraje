package standardsoft.com.coraje.ui.view.detailTaskModule;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import standardsoft.com.coraje.data.model.entities.Remark;
import standardsoft.com.coraje.ui.view.detailTaskModule.events.DetailTaskEvent;
import standardsoft.com.coraje.ui.view.detailTaskModule.model.DetailTaskInteractor;
import standardsoft.com.coraje.ui.view.detailTaskModule.model.DetailTaskInteractorClass;
import standardsoft.com.coraje.ui.view.detailTaskModule.view.DetailTaskView;

public class DetailTaskPresenterClass implements DetailTaskPresenter {
    private DetailTaskView mView;
    private DetailTaskInteractor mInteractor;

    public DetailTaskPresenterClass(DetailTaskView mView) {
        this.mView = mView;
        this.mInteractor = new DetailTaskInteractorClass();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume(String idSubPlanning) {
        if (mView != null) {
            mView.showProgress();
            mInteractor.subscribeToRemarkList(idSubPlanning);
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mView = null;
    }

    @Override
    public void addRemarkTask(String idPlanning, String idSubPlanning, Remark remark) {
        if (mView != null) {
            mView.disableUIElements();
            mView.showProgress();

            mInteractor.addRemarkTask(idPlanning, idSubPlanning, remark);
        }
    }

    @Subscribe
    @Override
    public void onEventListener(DetailTaskEvent event) {
        if (mView != null) {
            mView.hideProgress();
            mView.enableUIElements();

            switch (event.getTypeEvent()) {
                case DetailTaskEvent.REMARK_ADDED:
                    mView.clearUIElements();
                    mView.addRemarkTask();
                    break;
                case DetailTaskEvent.RESULT_REMARK:
                    mView.resultRemark(event.getRemarks());
                    break;
                case DetailTaskEvent.ERROR_SERVER:
                    mView.onShowError(event.getTypeEvent());
                    break;
            }
        }
    }
}
