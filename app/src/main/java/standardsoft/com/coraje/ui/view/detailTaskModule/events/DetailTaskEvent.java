package standardsoft.com.coraje.ui.view.detailTaskModule.events;

public class DetailTaskEvent {
    public static final int REMARK_ADDED    = 0;
    public static final int ERROR_SERVER    = 100;

    private int typeEvent;

    // Constructor vacio
    public DetailTaskEvent() {}

    // Get & Set
    public int getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }
}
