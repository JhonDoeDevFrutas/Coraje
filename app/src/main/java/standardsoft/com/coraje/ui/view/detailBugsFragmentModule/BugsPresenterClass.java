package standardsoft.com.coraje.ui.view.detailBugsFragmentModule;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import standardsoft.com.coraje.ui.view.detailBugsFragmentModule.events.DetailBugsEvent;
import standardsoft.com.coraje.ui.view.detailBugsFragmentModule.model.BugsInteractor;
import standardsoft.com.coraje.ui.view.detailBugsFragmentModule.model.BugsInteractorClass;
import standardsoft.com.coraje.ui.view.detailBugsFragmentModule.view.DetailBugsView;

public class BugsPresenterClass implements BugsPresenter {
    private DetailBugsView mView;
    private BugsInteractor mInteractor;

    public BugsPresenterClass(DetailBugsView mView) {
        this.mView = mView;
        this.mInteractor = new BugsInteractorClass();
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
            mInteractor.unsubscribeToBugsList();
        }
    }

    @Override
    public void onResume(String phone, boolean filter) {
        if (mView != null){
            mView.showProgress();
            mInteractor.subscribeToBugsList(phone, filter);
            mInteractor.subscribeToDeveloperList();
        }
    }

    @Subscribe
    @Override
    public void onEventListener(DetailBugsEvent event) {
        if (mView != null){
            mView.hideProgress();

            switch (event.getTypeEvent()){
                case DetailBugsEvent.BUGS_ADDED:
                    mView.requestDatas(event.getBugsList());
                    break;
                case DetailBugsEvent.RESULT_DEVELOPER:
                    mView.requestDeveloper(event.getDevelopers());
                    break;
                case DetailBugsEvent.ERROR_SERVER:
                    mView.showError(event.getResMsg());
                    break;
            }
        }

    }
}
