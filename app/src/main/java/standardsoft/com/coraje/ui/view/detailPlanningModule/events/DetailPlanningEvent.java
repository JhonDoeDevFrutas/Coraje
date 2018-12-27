package standardsoft.com.coraje.ui.view.detailPlanningModule.events;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Planning;

public class DetailPlanningEvent {
    public static final int PLANNING_ADDED = 0;
    public static final int ERROR_SERVER = 100;

    private List<Planning> plannings;
    private int typeEvent;
    private int resMsg;

    public DetailPlanningEvent() {
    }

    public List<Planning> getPlannings() {
        return plannings;
    }

    public void setPlannings(List<Planning> plannings) {
        this.plannings = plannings;
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
