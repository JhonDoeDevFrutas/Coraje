package standardsoft.com.coraje.ui.view.detailSubPlanningModule;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import standardsoft.com.coraje.data.model.entities.Planning;
import standardsoft.com.coraje.data.model.entities.SubPlanning;
import standardsoft.com.coraje.ui.view.detailSubPlanningModule.events.SubPlanningEvent;
import standardsoft.com.coraje.ui.view.detailSubPlanningModule.model.SubPlanningInteractor;
import standardsoft.com.coraje.ui.view.detailSubPlanningModule.model.SubPlanningInteractorClass;
import standardsoft.com.coraje.ui.view.detailSubPlanningModule.view.SubPlanningView;

public class SubPlanningPresenterClass implements SubPlanningPresenter {
    private SubPlanningView mView;
    private SubPlanningInteractor mInteractor;

    public SubPlanningPresenterClass(SubPlanningView mView) {
        this.mView = mView;
        this.mInteractor = new SubPlanningInteractorClass();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume(String idPlanning) {
        if (mView != null){
            mView.showProgress();
            mInteractor.subscribeToSubPlanningList(idPlanning);
            mView.showProgress();
            mInteractor.subscribeToDeveloperList();
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
    public void addSubPlanning(String idPlanning, SubPlanning subPlanning) {
        if (mView != null){
            mView.showProgress();

            mInteractor.addSubPlanning(idPlanning, subPlanning);
        }
    }

    @Subscribe
    @Override
    public void onEventListener(SubPlanningEvent event) {
        if (mView != null){
            mView.hideProgress();

            switch (event.getTypeEvent()){
                case  SubPlanningEvent.SUBPLANNING_ADDED:
                    mView.addSubPlanning();
                    break;
                case SubPlanningEvent.RESULT_DEVELOPER:
                    mView.requestDeveloper(event.getDevelopers());
                    break;
                case SubPlanningEvent.RESULT_SUB_PLANNING:
                    mView.requestSubPlanning(event.getSubPlannings());
                    break;
                case SubPlanningEvent.ERROR_SERVER:
                    mView.onShowError(event.getTypeEvent());
                    break;
            }
        }
    }
}
