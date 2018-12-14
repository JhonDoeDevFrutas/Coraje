package standardsoft.com.coraje.ui.view.planningModule.model;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import standardsoft.com.coraje.common.BasicErrorEventCallback;
import standardsoft.com.coraje.data.model.entities.Customer;
import standardsoft.com.coraje.data.model.entities.Planning;
import standardsoft.com.coraje.ui.view.planningModule.events.PlanningEvent;
import standardsoft.com.coraje.ui.view.planningModule.model.dataAccess.PlanningEventListener;
import standardsoft.com.coraje.ui.view.planningModule.model.dataAccess.RealtimeDatabase;

public class PlanningInteractorClass implements PlanningInteractor {

    private RealtimeDatabase mDatabase;

    public PlanningInteractorClass() {
        mDatabase = new RealtimeDatabase();
    }

    @Override
    public void subscribeToCustomer() {
        mDatabase.subscribeToPlanning(new PlanningEventListener() {
            @Override
            public void onDataChange(List<Customer> customerList) {
                post(customerList, PlanningEvent.SUCCESS_ADD);

            }

            @Override
            public void onError(int resMsg) {
                post(PlanningEvent.ERROR_SERVER, resMsg);
            }
        });

    }

    @Override
    public void unsubscribeToCustomer() {
        mDatabase.unsubscribeToPlanning();
    }

    @Override
    public void addPlanning(Planning planning) {
        mDatabase.addPlanning(planning, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(PlanningEvent.SUCCESS_ADD);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent, resMsg);
            }
        });
    }

    private void post(int typeEvent) {
        post(typeEvent, 0);
    }

    private void post(List<Customer> customers, int typeEvent){

    }

    private void post(int typeEvent, int resMsg) {
        PlanningEvent event = new PlanningEvent();
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }

    private void post(List<Customer> customers, int typeEvent, int resMsg){
        PlanningEvent event = new PlanningEvent();
        event.setCustomers(customers);
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }

}
