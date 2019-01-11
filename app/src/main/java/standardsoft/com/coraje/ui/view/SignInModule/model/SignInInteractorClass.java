package standardsoft.com.coraje.ui.view.SignInModule.model;

import org.greenrobot.eventbus.EventBus;

import standardsoft.com.coraje.data.model.entities.User;
import standardsoft.com.coraje.ui.view.SignInModule.events.SignInEvent;
import standardsoft.com.coraje.ui.view.SignInModule.model.dataAccess.RealtimeDatabase;
import standardsoft.com.coraje.ui.view.SignInModule.model.dataAccess.SignInEventListener;

public class SignInInteractorClass implements SignInInteractor {
    private RealtimeDatabase mDatabase;

    public SignInInteractorClass() {
        this.mDatabase = new RealtimeDatabase();
    }

    @Override
    public void subscribeToUser(String phone, String password) {
        mDatabase.subscribeToUser(phone, password, new SignInEventListener() {
            @Override
            public void onSuccess(User user) {
                post(SignInEvent.STATUS_AUTH_SUCCESS, user);
            }

            @Override
            public void onError() {
                post(SignInEvent.ERROR_SERVER);
            }
        });
    }

    private void post(int typeEvent, User user){
        post(typeEvent, user, 0);
    }

    private void post(int typeEvent) {
        post(typeEvent, null, 0);
    }

    private void post(int typeEvent, User user, int resMsg) {
        SignInEvent event = new SignInEvent();
        event.setTypeEvent(typeEvent);
        event.setUser(user);
        EventBus.getDefault().post(event);
    }
}
