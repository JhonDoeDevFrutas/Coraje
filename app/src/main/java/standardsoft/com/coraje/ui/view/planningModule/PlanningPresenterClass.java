package standardsoft.com.coraje.ui.view.planningModule;

import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import standardsoft.com.coraje.data.model.entities.Planning;
import standardsoft.com.coraje.ui.view.planningModule.events.PlanningEvent;
import standardsoft.com.coraje.ui.view.planningModule.model.PlanningInteractor;
import standardsoft.com.coraje.ui.view.planningModule.model.PlanningInteractorClass;
import standardsoft.com.coraje.ui.view.planningModule.view.PlanningView;

/**
 * ¿De que debería estar libre nuestro Presenter?
 * Del framework de android.
 * De conexiones directas a la base de datos
 */
public class PlanningPresenterClass implements PlanningPresenter {

    private PlanningView mView;
    private PlanningInteractor mInteractor;

    public PlanningPresenterClass(PlanningView mView) {
        this.mView = mView;
        this.mInteractor = new PlanningInteractorClass();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        mInteractor.subscribeToProject();
        mInteractor.subscribeToCustomer();
        mInteractor.subscribeToDeveloper();
    }

    @Override
    public void onPause() {
        mInteractor.unsubscribeToCustomer();
        mInteractor.unsubscribeToProject();
        mInteractor.unsubscribeToDeveloper();
    }

    @Override
    public void onDestroy() {
        mView = null;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void addPlanning(Planning planning) {
        if (setProgress()) {
            mInteractor.addPlanning(planning);
        }
    }

    @Override
    public void updatePlanning(Planning planning) {
        if (setProgress()) {
            mInteractor.updatePlanning(planning);
        }
    }

    private boolean setProgress() {
        if (mView != null) {
            mView.showProgress();
            return true;
        }
        return false;
    }

    @Subscribe
    @Override
    public void onEventListener(PlanningEvent event) {
        if (mView != null) {
            mView.hideProgress();

            switch (event.getTypeEvent()) {
                case PlanningEvent.RESULT_PROJECT:
                    mView.resultProject(event.getProjects());
                    break;
                case PlanningEvent.SUCCESS:
                    mView.resultCustomer(event.getCustomers());
                    break;

                case PlanningEvent.RESULT_DEVELOPER:
                    mView.resultDeveloper(event.getDevelopers());
                    break;

                case PlanningEvent.SUCCESS_ADD:
                    mView.planningAdded();
                    break;

                case PlanningEvent.ERROR_SERVER:
                    mView.onShowError(event.getResMsg());

                case PlanningEvent.UPDATE_SUCCESS:
                    mView.updateSuccess();
                    break;
            }
        }
    }

}
