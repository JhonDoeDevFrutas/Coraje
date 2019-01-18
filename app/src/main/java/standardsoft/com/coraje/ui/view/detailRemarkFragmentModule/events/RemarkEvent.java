package standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.events;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Remark;

public class RemarkEvent {
    public static final int RESULT_REMARK   = 0;
    public static final int ERROR_SERVER    = 100;

    private List<Remark> remarks;
    private int typeEvent;
    private int resMsg;

    public RemarkEvent() {
    }

    // get & set
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

    public int getResMsg() {
        return resMsg;
    }

    public void setResMsg(int resMsg) {
        this.resMsg = resMsg;
    }
}
