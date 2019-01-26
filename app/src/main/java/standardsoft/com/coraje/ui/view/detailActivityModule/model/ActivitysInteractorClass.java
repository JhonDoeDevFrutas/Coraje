package standardsoft.com.coraje.ui.view.detailActivityModule.model;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.SubPlanning;
import standardsoft.com.coraje.ui.view.detailActivityModule.events.ActivitysEvent;
import standardsoft.com.coraje.ui.view.detailActivityModule.model.dataAccess.ActivitysEventListener;
import standardsoft.com.coraje.ui.view.detailActivityModule.model.dataAccess.RealtimeDatabase;

public class ActivitysInteractorClass implements ActivitysInteractor {
    private RealtimeDatabase mDatabase;

    public ActivitysInteractorClass() {
        mDatabase = new RealtimeDatabase();
    }

    @Override
    public void subscribeToSubPlanningList(String phone, boolean selectAll) {
        mDatabase.subscribeToSubPlanningList(phone, selectAll, new ActivitysEventListener() {
            @Override
            public void onDataChange(List<SubPlanning> subPlanningList) {
                post(ActivitysEvent.SUBPLANNING_ADDED, subPlanningList);
            }

            @Override
            public void onDataDeveloper(List<Developer> developerList) {

            }

            @Override
            public void onError(int resMsg) {

            }
        });

    }

    @Override
    public void subscribeToDeveloperList() {
        mDatabase.subscribeToDeveloperList(new ActivitysEventListener() {
            @Override
            public void onDataChange(List<SubPlanning> subPlanningList) {

            }

            @Override
            public void onDataDeveloper(List<Developer> developerList) {
                postDeveloper(developerList, ActivitysEvent.RESULT_DEVELOPER);// result developers
            }

            @Override
            public void onError(int resMsg) {

            }
        });

    }

    @Override
    public void unsubscribeToSubPlanningList() {

    }

    private void post(int typeEvent, List<SubPlanning> subPlannings){
        post(typeEvent, subPlannings, 0);
    }

    private void post(int typeEvent, List<SubPlanning> subPlannings, int resMsg) {
        ActivitysEvent event = new ActivitysEvent();
        event.setTypeEvent(typeEvent);
        event.setSubPlannings(subPlannings);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }

    // result developer
    private void postDeveloper(List<Developer> developers, int typeEvent){
        ActivitysEvent event = new ActivitysEvent();
        event.setDevelopers(developers);
        event.setTypeEvent(typeEvent);
        EventBus.getDefault().post(event);
    }

}
