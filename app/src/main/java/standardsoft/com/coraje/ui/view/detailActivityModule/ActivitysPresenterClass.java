package standardsoft.com.coraje.ui.view.detailActivityModule;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import standardsoft.com.coraje.ui.view.detailActivityModule.events.ActivitysEvent;
import standardsoft.com.coraje.ui.view.detailActivityModule.model.ActivitysInteractor;
import standardsoft.com.coraje.ui.view.detailActivityModule.model.ActivitysInteractorClass;
import standardsoft.com.coraje.ui.view.detailActivityModule.view.ActivitysView;

public class ActivitysPresenterClass implements ActivitysPresenter {
    private ActivitysView mView;
    private ActivitysInteractor mInteractor;

    public ActivitysPresenterClass(ActivitysView mView) {
        this.mView = mView;
        this.mInteractor = new ActivitysInteractorClass();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mView = null;
    }

    @Override
    public void onPause() {
        if (mView != null){
            mInteractor.unsubscribeToSubPlanningList();
        }
    }

    @Override
    public void onResume(String phone, boolean selectAll) {
        if (mView != null){
            mView.showProgress();
            mInteractor.subscribeToSubPlanningList(phone, selectAll);
            mInteractor.subscribeToDeveloperList();
        }
    }

    @Subscribe
    @Override
    public void onEventListener(ActivitysEvent event) {
        if (mView != null){
            mView.hideProgress();

            switch (event.getTypeEvent()){
                case ActivitysEvent.SUBPLANNING_ADDED:
                    mView.resultSubPlanning(event.getSubPlannings());
                    break;
                case ActivitysEvent.RESULT_DEVELOPER:
                    mView.requestDeveloper(event.getDevelopers());
                    break;
                case ActivitysEvent.ERROR_SERVER:
                    mView.showError(event.getResMsg());
                    break;
            }
        }
    }
}
