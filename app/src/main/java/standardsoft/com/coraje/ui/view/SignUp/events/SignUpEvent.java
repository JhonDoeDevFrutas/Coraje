package standardsoft.com.coraje.ui.view.SignUp.events;

public class SignUpEvent {
    public static final int USER_ADDED      = 0;
    public static final int ERROR_SERVER    = 100;

    private int typeEvent;

    // Constructor vacio
    public SignUpEvent() {
    }

    // Get & Set
    public int getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }
}
