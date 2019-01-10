package standardsoft.com.coraje.ui.view.taskModule.model;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import standardsoft.com.coraje.common.BasicErrorEventCallback;
import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.SubPlanning;
import standardsoft.com.coraje.ui.view.taskModule.events.TaskEvent;
import standardsoft.com.coraje.ui.view.taskModule.model.dataAccess.RealtimeDatabase;
import standardsoft.com.coraje.ui.view.taskModule.model.dataAccess.TaskEventListener;

public class TaskInteractorClass implements TaskInteractor {

    private RealtimeDatabase mDatabase;

    public TaskInteractorClass(){
        mDatabase = new RealtimeDatabase();
    }

    @Override
    public void subscribeToDeveloper() {
        mDatabase.subscribeToDeveloper(new TaskEventListener() {
            @Override
            public void onDataDeveloper(List<Developer> developerList) {
                post(developerList, TaskEvent.RESULT_DEVELOPER);// result developers
            }

            @Override
            public void onError(int resMsg) {

            }
        });
    }

    @Override
    public void unsubscribeToDeveloper() {
        mDatabase.unsubscribeToDeveloper();
    }

    @Override
    public void update(String idPlanning, SubPlanning subPlanning) {
        mDatabase.update(idPlanning, subPlanning, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(TaskEvent.UPDATE_SUCCESS);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {

            }

            @Override
            public void onError() {

            }
        });

    }

    private void post(int typeEvent) {
        post(typeEvent, 0);
    }

    private void post(List<Developer> developers, int typeEvent){
        post(developers, typeEvent, 0); // result customers
    }

    private void post(List<Developer> developers, int typeEvent, int resMsg){
        TaskEvent event = new TaskEvent();
        event.setDevelopers(developers);
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }

    private void post(int typeEvent, int resMsg) {
        TaskEvent event = new TaskEvent();
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }

}
