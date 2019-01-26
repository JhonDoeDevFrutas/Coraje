package standardsoft.com.coraje.ui.view.promptTaskModule;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import standardsoft.com.coraje.data.model.entities.PromptTask;
import standardsoft.com.coraje.ui.view.promptTaskModule.events.PromptTaskEvent;
import standardsoft.com.coraje.ui.view.promptTaskModule.model.PromptTaskInteractor;
import standardsoft.com.coraje.ui.view.promptTaskModule.model.PromptTaskInteractorClass;
import standardsoft.com.coraje.ui.view.promptTaskModule.view.PromptView;

public class PromptTaskPresenterClass implements PromptTaskPresenter {

    private PromptView mView;
    private PromptTaskInteractor mInteractor;

    public PromptTaskPresenterClass(PromptView mView) {
        this.mView = mView;
        this.mInteractor = new PromptTaskInteractorClass();
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
    public void addPromptTask(PromptTask promptTask) {
        if (setProgress()) {
            mInteractor.addPromptTask(promptTask);
        }
    }

    @Override
    public void updatePromptTask(PromptTask promptTask) {
        if (setProgress()) {
            mInteractor.updatePromptTask(promptTask);
        }
    }

    @Subscribe
    @Override
    public void onEventListener(PromptTaskEvent event) {
        if (mView != null) {
            mView.hideProgress();

            switch (event.getTypeEvent()) {
                case PromptTaskEvent.RESULT_PROJECT:
                    mView.resultProject(event.getProjects());
                    break;
                case PromptTaskEvent.SUCCESS:
                    mView.resultCustomer(event.getCustomers());
                    break;

                case PromptTaskEvent.RESULT_DEVELOPER:
                    mView.resultDeveloper(event.getDevelopers());
                    break;

                case PromptTaskEvent.SUCCESS_ADD:
                    mView.planningAdded();
                    break;

                case PromptTaskEvent.ERROR_SERVER:
                    mView.onShowError(event.getResMsg());

                case PromptTaskEvent.UPDATE_SUCCESS:
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
