package standardsoft.com.coraje.ui.view.SignInModule;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import standardsoft.com.coraje.data.model.entities.User;
import standardsoft.com.coraje.ui.view.SignInModule.events.SignInEvent;
import standardsoft.com.coraje.ui.view.SignInModule.model.SignInInteractor;
import standardsoft.com.coraje.ui.view.SignInModule.model.SignInInteractorClass;
import standardsoft.com.coraje.ui.view.SignInModule.view.SignInView;

public class SignInPresenterClass implements SignInPresenter{
    private SignInView mView;
    private SignInInteractor mInteractor;

    public SignInPresenterClass(SignInView mView) {
        this.mView = mView;
        this.mInteractor = new SignInInteractorClass();
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
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
    public void subscribeToUser(String phone, String password) {
        if (mView != null){
            mView.showProgress();

            mInteractor.subscribeToUser(phone, password);
        }
    }

    @Subscribe
    @Override
    public void onEventListener(SignInEvent event) {
        if (mView != null){
            mView.hideProgress();
            User user = event.getUser();

            switch (event.getTypeEvent()){
                case  SignInEvent.STATUS_AUTH_SUCCESS:
                    mView.openHomeActivity(user);
                    break;
                case SignInEvent.ERROR_SERVER:
                    mView.onShowError(event.getTypeEvent());
                    break;
            }
        }

    }
}
