package standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.model;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Remark;
import standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.events.RemarkEvent;
import standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.model.dataAccess.DetailRemarkEventListener;
import standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.model.dataAccess.RealtimeDatabase;

public class RemarkInteractorClass implements RemarkInteractor {

    private RealtimeDatabase mDatabase;

    public RemarkInteractorClass() {
        this.mDatabase = new RealtimeDatabase();
    }

    @Override
    public void subscribeToRemarkList() {
        mDatabase.subscribeToRemarkList(new DetailRemarkEventListener() {
            @Override
            public void onDataChange(List<Remark> remarkList) {
                post(RemarkEvent.RESULT_REMARK, remarkList);
            }

            @Override
            public void onError(int resMsg) {

            }
        });
    }

    private void post(int typeEvent, List<Remark> subPlannings){
        post(typeEvent, subPlannings, 0);
    }

    private void post(int typeEvent, List<Remark> remarks, int resMsg) {
        RemarkEvent event = new RemarkEvent();
        event.setTypeEvent(typeEvent);
        event.setRemarks(remarks);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }

}
