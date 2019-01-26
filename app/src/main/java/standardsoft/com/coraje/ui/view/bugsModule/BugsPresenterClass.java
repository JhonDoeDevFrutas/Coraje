package standardsoft.com.coraje.ui.view.bugsModule;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import standardsoft.com.coraje.data.model.entities.Bugs;
import standardsoft.com.coraje.ui.view.bugsModule.events.BugsEvent;
import standardsoft.com.coraje.ui.view.bugsModule.model.BugsInteractor;
import standardsoft.com.coraje.ui.view.bugsModule.model.BugsInteractorClass;
import standardsoft.com.coraje.ui.view.bugsModule.view.BugsView;

public class BugsPresenterClass implements BugsPresenter {

    private BugsView mView;
    private BugsInteractor mInteractor;

    public BugsPresenterClass(BugsView mView) {
        this.mView = mView;
        this.mInteractor = new BugsInteractorClass();
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
    public void addBugs(Bugs bugs) {
        if (setProgress()) {
            mInteractor.addBugs(bugs);
        }
    }

    @Override
    public void updateBugs(Bugs bugs) {
        if (setProgress()) {
            mInteractor.updateBugs(bugs);
        }
    }

    @Subscribe
    @Override
    public void onEventListener(BugsEvent event) {
        if (mView != null) {
            mView.hideProgress();

            switch (event.getTypeEvent()) {
                case BugsEvent.RESULT_PROJECT:
                    mView.resultProject(event.getProjects());
                    break;
                case BugsEvent.SUCCESS:
                    mView.resultCustomer(event.getCustomers());
                    break;

                case BugsEvent.RESULT_DEVELOPER:
                    mView.resultDeveloper(event.getDevelopers());
                    break;

                case BugsEvent.SUCCESS_ADD:
                    mView.planningAdded();
                    break;

                case BugsEvent.ERROR_SERVER:
                    mView.onShowError(event.getResMsg());

                case BugsEvent.UPDATE_SUCCESS:
                    mView.updateSuccess();
                    break;
            }
        }

    }

    private boolean setProgress() {
        if (mView != null) {
            mView.showProgress();
            return true;
        }
        return false;
    }

}
