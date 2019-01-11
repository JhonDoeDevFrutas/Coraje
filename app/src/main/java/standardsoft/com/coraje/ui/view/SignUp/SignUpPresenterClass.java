package standardsoft.com.coraje.ui.view.SignUp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import standardsoft.com.coraje.data.model.entities.User;
import standardsoft.com.coraje.ui.view.SignUp.events.SignUpEvent;
import standardsoft.com.coraje.ui.view.SignUp.model.SignUpInteractor;
import standardsoft.com.coraje.ui.view.SignUp.model.SignUpInteractorClass;
import standardsoft.com.coraje.ui.view.SignUp.view.SignUpView;

public class SignUpPresenterClass implements SignUpPresenter{
    private SignUpView mView;
    private SignUpInteractor mInteractor;

    public SignUpPresenterClass(SignUpView mView) {
        this.mView = mView;
        this.mInteractor = new SignUpInteractorClass();
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
    public void addUser(User user) {
        if (mView != null){
            mView.showProgress();

            mInteractor.addUser(user);
        }
    }

    @Subscribe
    @Override
    public void onEventListener(SignUpEvent event) {
        if (mView != null){
            mView.hideProgress();

            switch (event.getTypeEvent()){
                case  SignUpEvent.USER_ADDED:
                    mView.addUser();
                    break;
                case SignUpEvent.ERROR_SERVER:
                    mView.onShowError(event.getTypeEvent());
                    break;
            }
        }

    }
}
