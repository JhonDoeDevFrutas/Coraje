package standardsoft.com.coraje.ui.view.taskModule;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import standardsoft.com.coraje.data.model.entities.SubPlanning;
import standardsoft.com.coraje.ui.view.taskModule.events.TaskEvent;
import standardsoft.com.coraje.ui.view.taskModule.model.TaskInteractor;
import standardsoft.com.coraje.ui.view.taskModule.model.TaskInteractorClass;
import standardsoft.com.coraje.ui.view.taskModule.view.TaskView;

public class TaskPresenterClass implements TaskPresenter {

    private TaskView mView;
    private TaskInteractor mInteractor;

    public TaskPresenterClass(TaskView mView) {
        this.mView = mView;
        this.mInteractor = new TaskInteractorClass();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        mInteractor.subscribeToDeveloper();
    }

    @Override
    public void onPause() {
        mInteractor.unsubscribeToDeveloper();
    }

    @Override
    public void onDestroy() {
        mView = null;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void update(String idPlanning, SubPlanning subPlanning) {
        if (setProgress()) {
            mInteractor.update(idPlanning, subPlanning);
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
    public void onEventListener(TaskEvent event) {
        if (mView != null){
            mView.hideProgress();

            switch (event.getTypeEvent()){
                case TaskEvent.RESULT_DEVELOPER:
                    mView.resultDeveloper(event.getDevelopers());
                    break;
                case TaskEvent.UPDATE_SUCCESS:
                    mView.updateSuccess();
                    break;
            }
        }

    }
}
