package standardsoft.com.coraje.ui.view.detailTaskModule.events;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Remark;

public class DetailTaskEvent {
    public static final int REMARK_ADDED    = 0;
    public static final int RESULT_REMARK   = 1;
    public static final int ERROR_SERVER    = 100;

    private List<Remark> remarks;
    private int typeEvent;

    // Constructor vacio
    public DetailTaskEvent() {}

    // Get & Set

    public List<Remark> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<Remark> remarks) {
        this.remarks = remarks;
    }

    public int getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }
}
