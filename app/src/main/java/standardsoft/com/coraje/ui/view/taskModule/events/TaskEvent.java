package standardsoft.com.coraje.ui.view.taskModule.events;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Developer;

public class TaskEvent {
    public static final int RESULT_DEVELOPER    = 0;
    public static final int UPDATE_SUCCESS      = 1;

    public static final int ERROR_SERVER        = 100;

    private List<Developer> developers;
    private int typeEvent;
    private int resMsg;


    // Constructor vacio
    public TaskEvent() {}

    // Get & Set
    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
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
