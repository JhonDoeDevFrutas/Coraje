package standardsoft.com.coraje.ui.view.detailPlanningModule;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import standardsoft.com.coraje.data.model.entities.Planning;
import standardsoft.com.coraje.ui.view.detailPlanningModule.events.DetailPlanningEvent;
import standardsoft.com.coraje.ui.view.detailPlanningModule.model.DetailPlanningInteractor;
import standardsoft.com.coraje.ui.view.detailPlanningModule.model.DetailPlanningInteractorClass;
import standardsoft.com.coraje.ui.view.detailPlanningModule.view.DetailPlanningView;

public class DetailPlanningPresenterClass implements DetailPlanningPresenter {
    private DetailPlanningView mView;
    private DetailPlanningInteractor mInteractor;

    public DetailPlanningPresenterClass(DetailPlanningView mView) {
        this.mView = mView;
        this.mInteractor = new DetailPlanningInteractorClass();
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
            mInteractor.unsubscribeToPlanningList();
        }
    }

    @Override
    public void onResume() {
        if (mView != null){
            mInteractor.subscribeToPlanningList();
        }
    }

    @Subscribe
    @Override
    public void onEventListener(DetailPlanningEvent event) {
        if (mView != null){
            switch (event.getTypeEvent()){
                case DetailPlanningEvent.PLANNING_ADDED:
                    mView.addDatas(event.getPlannings());
                    break;
                case DetailPlanningEvent.ERROR_SERVER:
                    mView.showError(event.getResMsg());
                    break;
            }
        }
    }

}
