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
    public void addRemarkTask(String idSubPlanning, Remark remark) {
        if (mView != null){
            mView.showProgress();

            mInteractor.addRemarkTask(idSubPlanning, remark);
        }
    }

    @Subscribe
    @Override
    public void onEventListener(DetailTaskEvent event) {
        if (mView != null){
            mView.hideProgress();

            switch (event.getTypeEvent()){
                case DetailTaskEvent.REMARK_ADDED:
                    mView.addRemarkTask();
                    break;
                case DetailTaskEvent.ERROR_SERVER:
                    mView.onShowError(event.getTypeEvent());
                    break;
            }
        }
    }
}
