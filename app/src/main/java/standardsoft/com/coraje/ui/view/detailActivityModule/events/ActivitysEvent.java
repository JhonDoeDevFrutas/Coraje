package standardsoft.com.coraje.ui.view.detailActivityModule.events;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.SubPlanning;

public class ActivitysEvent {
    public static final int SUBPLANNING_ADDED = 0;
    public static final int RESULT_DEVELOPER  = 1;
    public static final int ERROR_SERVER = 100;

    private List<SubPlanning> subPlannings;
    private List<Developer> developers;
    private int typeEvent;
    private int resMsg;

    // Constructor vacio
    public ActivitysEvent() {}

    // Get & Set
    public List<SubPlanning> getSubPlannings() {
        return subPlannings;
    }

    public void setSubPlannings(List<SubPlanning> subPlannings) {
        this.subPlannings = subPlannings;
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
