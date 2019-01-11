package standardsoft.com.coraje.ui.view.SignInModule.events;

import standardsoft.com.coraje.data.model.entities.User;

public class SignInEvent {
    public static final int STATUS_AUTH_SUCCESS = 0;
    public static final int ERROR_SERVER        = 100;
    public static final int USER_NOT_EXIST      = 102;

    private User user;
    private int typeEvent;
    private int resMsg;

    // Constructor vacio
    public SignInEvent() {
    }

    // Get & Set
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }

    public int getResMsg() {
        return resMsg;
    }

    public void setResMsg(int resMsg) {
        this.resMsg = resMsg;
    }
}
