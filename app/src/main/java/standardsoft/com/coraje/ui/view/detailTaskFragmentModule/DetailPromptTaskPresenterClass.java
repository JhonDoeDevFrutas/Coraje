package standardsoft.com.coraje.ui.view.detailTaskFragmentModule;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import standardsoft.com.coraje.ui.view.detailTaskFragmentModule.events.PromptTaskEvent;
import standardsoft.com.coraje.ui.view.detailTaskFragmentModule.model.DetailPromptTaskInteractor;
import standardsoft.com.coraje.ui.view.detailTaskFragmentModule.model.DetailPromptTaskInteractorClass;
import standardsoft.com.coraje.ui.view.detailTaskFragmentModule.view.DetailPromptTaskView;

public class DetailPromptTaskPresenterClass implements DetailPromptTaskPresenter {

    private DetailPromptTaskView mView;
    private DetailPromptTaskInteractor mInteractor;

    public DetailPromptTaskPresenterClass(DetailPromptTaskView mView) {
        this.mView = mView;
        this.mInteractor = new DetailPromptTaskInteractorClass();
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

        }
    }

    @Override
    public void onResume(String phone, boolean filter) {
        if (mView != null){
            mView.showProgress();
            mInteractor.subscribeToPromptTaskList(phone, filter);
            mInteractor.subscribeToDeveloperList();
        }
    }

    @Subscribe
    @Override
    public void onEventListener(PromptTaskEvent event) {
        if (mView != null){
            mView.hideProgress();

            switch (event.getTypeEvent()){
                case PromptTaskEvent.PROMTP_TASK_ADDED:
                    mView.requestDatas(event.getPromptTaskList());
                    break;
                case PromptTaskEvent.RESULT_DEVELOPER:
                    mView.requestDeveloper(event.getDevelopers());
                    break;
                case PromptTaskEvent.ERROR_SERVER:
                    mView.showError(event.getResMsg());
                    break;
            }
        }

    }
}
