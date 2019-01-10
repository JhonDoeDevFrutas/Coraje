package standardsoft.com.coraje.ui.view.detailTaskModule.model;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import standardsoft.com.coraje.common.BasicErrorEventCallback;
import standardsoft.com.coraje.data.model.entities.Remark;
import standardsoft.com.coraje.ui.view.detailTaskModule.events.DetailTaskEvent;
import standardsoft.com.coraje.ui.view.detailTaskModule.model.dataAccess.DetailEventListener;
import standardsoft.com.coraje.ui.view.detailTaskModule.model.dataAccess.RealtimeDatabase;

public class DetailTaskInteractorClass implements DetailTaskInteractor {

    private RealtimeDatabase mDatabase;

    public DetailTaskInteractorClass() {
        this.mDatabase = new RealtimeDatabase();
    }

    @Override
    public void subscribeToRemarkList(String idSubPlanning) {
        mDatabase.subscribeToRemarkList(idSubPlanning, new DetailEventListener() {
            @Override
            public void onDataChange(List<Remark> remarkList) {
                post(remarkList, DetailTaskEvent.RESULT_REMARK);
            }

            @Override
            public void onError(int resMsg) {
                post(DetailTaskEvent.ERROR_SERVER);
            }
        });

    }

    @Override
    public void addRemarkTask(String idPlanning, String idSubPlanning, Remark remark) {
        mDatabase.addRemarkTask(idPlanning, idSubPlanning, remark, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(DetailTaskEvent.REMARK_ADDED);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(DetailTaskEvent.ERROR_SERVER);
            }

            @Override
            public void onError() {
                post(DetailTaskEvent.ERROR_SERVER);
            }
        });
    }

    // result remark
    private void post(List<Remark> remarks, int typeEvent){
        DetailTaskEvent event = new DetailTaskEvent();
        event.setRemarks(remarks);
        event.setTypeEvent(typeEvent);
        EventBus.getDefault().post(event);
    }


    private void post(int typeEvent){
        DetailTaskEvent event = new DetailTaskEvent();
        event.setTypeEvent(typeEvent);
        EventBus.getDefault().post(event);
    }

}
