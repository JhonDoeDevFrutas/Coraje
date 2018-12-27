package standardsoft.com.coraje.ui.view.detailTaskModule.model;

import org.greenrobot.eventbus.EventBus;

import standardsoft.com.coraje.common.BasicErrorEventCallback;
import standardsoft.com.coraje.data.model.entities.Remark;
import standardsoft.com.coraje.ui.view.detailTaskModule.events.DetailTaskEvent;
import standardsoft.com.coraje.ui.view.detailTaskModule.model.dataAccess.RealtimeDatabase;

public class DetailTaskInteractorClass implements DetailTaskInteractor {

    private RealtimeDatabase mDatabase;

    @Override
    public void addRemarkTask(String idSubPlanning, Remark remark) {
        mDatabase.addRemarkTask(idSubPlanning, remark, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(DetailTaskEvent.ERROR_SERVER);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {

            }

            @Override
            public void onError() {

            }
        });
    }

    private void post(int typeEvent){
        DetailTaskEvent event = new DetailTaskEvent();
        event.setTypeEvent(typeEvent);
        EventBus.getDefault().post(event);
    }

}
