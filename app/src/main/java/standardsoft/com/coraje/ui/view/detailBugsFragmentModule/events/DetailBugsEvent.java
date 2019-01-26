package standardsoft.com.coraje.ui.view.detailBugsFragmentModule.events;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Bugs;
import standardsoft.com.coraje.data.model.entities.Developer;

public class DetailBugsEvent {
    public static final int BUGS_ADDED = 0;
    public static final int RESULT_DEVELOPER  = 1;
    public static final int ERROR_SERVER = 100;

    private List<Bugs> bugsList;
    private List<Developer> developers;
    private int typeEvent;
    private int resMsg;

    public DetailBugsEvent() {
    }

    // get & set
    public List<Bugs> getBugsList() {
        return bugsList;
    }

    public void setBugsList(List<Bugs> bugsList) {
        this.bugsList = bugsList;
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

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }
}
