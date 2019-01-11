package standardsoft.com.coraje.ui.view.SignUp.model;

import org.greenrobot.eventbus.EventBus;

import standardsoft.com.coraje.data.model.entities.User;
import standardsoft.com.coraje.ui.view.SignUp.events.SignUpEvent;
import standardsoft.com.coraje.ui.view.SignUp.model.dataAccess.RealtimeDatabase;
import standardsoft.com.coraje.ui.view.SignUp.model.dataAccess.SignUpEventListener;

public class SignUpInteractorClass implements SignUpInteractor{
    private RealtimeDatabase mDatabase;

    public SignUpInteractorClass() {
        this.mDatabase = new RealtimeDatabase();
    }

    @Override
    public void addUser(User user) {
        mDatabase.addUser(user, new SignUpEventListener() {
            @Override
            public void onSuccess() {
                post(SignUpEvent.USER_ADDED);
            }

            @Override
            public void onError() {

            }

            @Override
            public void onError(int resMsg) {
                post(SignUpEvent.ERROR_SERVER);
            }
        });
    }

    private void post(int typeEvent){
        SignUpEvent event = new SignUpEvent();
        event.setTypeEvent(typeEvent);
        EventBus.getDefault().post(event);
    }
}
