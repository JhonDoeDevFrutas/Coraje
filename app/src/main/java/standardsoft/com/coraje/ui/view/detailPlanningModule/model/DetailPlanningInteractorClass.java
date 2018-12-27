package standardsoft.com.coraje.ui.view.detailPlanningModule.model;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Planning;
import standardsoft.com.coraje.ui.view.detailPlanningModule.events.DetailPlanningEvent;
import standardsoft.com.coraje.ui.view.detailPlanningModule.model.dataAccess.PlanningEventListener;
import standardsoft.com.coraje.ui.view.detailPlanningModule.model.dataAccess.RealtimeDatabase;

public class DetailPlanningInteractorClass implements DetailPlanningInteractor {

    private RealtimeDatabase mDatabase;

    public DetailPlanningInteractorClass() {
        mDatabase = new RealtimeDatabase();
    }


    @Override
    public void subscribeToPlanningList() {
        mDatabase.subscribeToPlanningList(new PlanningEventListener() {
            @Override
            public void onDataChange(List<Planning> planningList) {
                post(DetailPlanningEvent.PLANNING_ADDED, planningList);
            }

            @Override
            public void onError(int resMsg) {

            }
        });

    }

    @Override
    public void unsubscribeToPlanningList() {

    }

    private void post(int typeEvent, List<Planning> plannings){
        post(typeEvent, plannings, 0);
    }

    private void post(int typeEvent, List<Planning> plannings, int resMsg) {
        DetailPlanningEvent event = new DetailPlanningEvent();
        event.setTypeEvent(typeEvent);
        event.setPlannings(plannings);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }
}
