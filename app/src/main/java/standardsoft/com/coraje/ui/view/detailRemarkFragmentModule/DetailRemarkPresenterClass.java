package standardsoft.com.coraje.ui.view.detailRemarkFragmentModule;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.events.RemarkEvent;
import standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.model.RemarkInteractor;
import standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.model.RemarkInteractorClass;
import standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.view.RemarkView;

public class DetailRemarkPresenterClass implements DetailRemarkPresenter {

    private RemarkView mView;
    private RemarkInteractor mInteractor;

    public DetailRemarkPresenterClass(RemarkView mView) {
        this.mView = mView;
        this.mInteractor = new RemarkInteractorClass();
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
    public void onResume() {
        if (mView != null){
            mView.showProgress();
            mInteractor.subscribeToSubPlanningList();
        }
    }

    @Subscribe
    @Override
    public void onEventListener(RemarkEvent event) {
        if (mView != null){
            mView.hideProgress();

            switch (event.getTypeEvent()){
                case RemarkEvent.RESULT_REMARK:
                    mView.resultRemark(event.getRemarks());
                    break;
                case RemarkEvent.RESULT_SUBPLANNING:
                    mView.resultSubPlanning(event.getSubPlannings());
                    break;
                case RemarkEvent.RESULT_BUGS:
                    mView.resultBugs(event.getBugsList());
                    break;
                case RemarkEvent.RESULT_PROMPT:
                    mView.resultPrompt(event.getPromptTaskList());
                    break;
                case RemarkEvent.ERROR_SERVER:
                    mView.showError(event.getResMsg());
                    break;
            }
        }
    }
}
