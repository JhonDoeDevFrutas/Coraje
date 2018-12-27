package standardsoft.com.coraje.ui.view.detailSubPlanningModule.events;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.Planning;
import standardsoft.com.coraje.data.model.entities.SubPlanning;

public class SubPlanningEvent {
    public static final int SUBPLANNING_ADDED       = 0;
    public static final int RESULT_DEVELOPER        = 1;
    public static final int RESULT_SUB_PLANNING     = 2;
    public static final int RESULT_PLANNING         = 3;
    public static final int ERROR_SERVER            = 100;

    private Planning planning;
    private List<SubPlanning> subPlannings;
    private List<Developer> developers;
    private int typeEvent;

    // Constructor vacio
    public SubPlanningEvent() {}

    // Get & Set
    public int getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

    public List<SubPlanning> getSubPlannings() {
        return subPlannings;
    }

    public void setSubPlannings(List<SubPlanning> subPlannings) {
        this.subPlannings = subPlannings;
    }

    public Planning getPlanning() {
        return planning;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
    }
}
