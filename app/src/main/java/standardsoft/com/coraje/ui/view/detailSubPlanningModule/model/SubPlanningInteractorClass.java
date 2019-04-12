package standardsoft.com.coraje.ui.view.detailSubPlanningModule.model;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import standardsoft.com.coraje.common.BasicErrorEventCallback;
import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.SubPlanning;
import standardsoft.com.coraje.ui.view.detailSubPlanningModule.events.SubPlanningEvent;
import standardsoft.com.coraje.ui.view.detailSubPlanningModule.model.dataAcces.RealtimeDatabase;
import standardsoft.com.coraje.ui.view.detailSubPlanningModule.model.dataAcces.SubPlanningEventListener;

public class SubPlanningInteractorClass implements SubPlanningInteractor {

    private RealtimeDatabase mDatabase;

    public SubPlanningInteractorClass() {
        this.mDatabase = new RealtimeDatabase();
    }

    @Override
    public void subscribeToDeveloperList() {
        mDatabase.subscribeToDeveloperList(new SubPlanningEventListener() {
            @Override
            public void onDataSubPlanning(List<SubPlanning> subPlanningList) {

            }

            @Override
            public void onDataDeveloper(List<Developer> developerList) {
                postDeveloper(developerList, SubPlanningEvent.RESULT_DEVELOPER);// result developers
            }

            @Override
            public void onError(int resMsg) {
                post(SubPlanningEvent.ERROR_SERVER);
            }
        });
    }

    @Override
    public void subscribeToSubPlanningList(String idPlanning) {
        mDatabase.subscribeToSubPlanningList(idPlanning, new SubPlanningEventListener() {
            @Override
            public void onDataSubPlanning(List<SubPlanning> subPlanningList) {
                post(subPlanningList, SubPlanningEvent.RESULT_SUB_PLANNING);
           }

            @Override
            public void onDataDeveloper(List<Developer> developerList) {

            }

            @Override
            public void onError(int resMsg) {
                post(SubPlanningEvent.ERROR_SERVER);
            }
        });
    }

    @Override
    public void addSubPlanning(String idPlanning, SubPlanning subPlanning) {
        mDatabase.addSubPlanning(idPlanning, subPlanning, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(SubPlanningEvent.SUBPLANNING_ADDED);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(SubPlanningEvent.ERROR_SERVER);
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void removeSubPlanning(String idPlanning, SubPlanning subPlanning) {
        mDatabase.removeSubPlanning(idPlanning, subPlanning, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(SubPlanningEvent.SUCCESS_REMOVE);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {

            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void updateSubPlanning(SubPlanning subPlanning) {
    }

    // result subplanning
    private void post(List<SubPlanning> subPlannings, int typeEvent){
        SubPlanningEvent event = new SubPlanningEvent();
        event.setSubPlannings(subPlannings);
        event.setTypeEvent(typeEvent);
        EventBus.getDefault().post(event);
    }

    // result developer
    private void postDeveloper(List<Developer> developers, int typeEvent){
        SubPlanningEvent event = new SubPlanningEvent();
        event.setDevelopers(developers);
        event.setTypeEvent(typeEvent);
        EventBus.getDefault().post(event);
    }

    private void post(int typeEvent){
        SubPlanningEvent event = new SubPlanningEvent();
        event.setTypeEvent(typeEvent);
        EventBus.getDefault().post(event);
    }
}
