package standardsoft.com.coraje.ui.view.bugsModule.model;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import standardsoft.com.coraje.common.BasicErrorEventCallback;
import standardsoft.com.coraje.data.model.entities.Bugs;
import standardsoft.com.coraje.data.model.entities.Customer;
import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.Project;
import standardsoft.com.coraje.ui.view.bugsModule.events.BugsEvent;
import standardsoft.com.coraje.ui.view.bugsModule.model.dataAccess.BugsEventListener;
import standardsoft.com.coraje.ui.view.bugsModule.model.dataAccess.RealtimeDatabase;

public class BugsInteractorClass implements BugsInteractor {

    private RealtimeDatabase mDatabase;

    public BugsInteractorClass() {
        mDatabase = new RealtimeDatabase();
    }

    @Override
    public void subscribeToProject() {
        mDatabase.subscribeToProject(new BugsEventListener() {
            @Override
            public void onDataChangeProject(List<Project> projectList) {
                postProject(projectList, BugsEvent.RESULT_PROJECT);// result projects
            }

            @Override
            public void onDataChange(List<Customer> customerList) {

            }

            @Override
            public void onDataDeveloper(List<Developer> developerList) {

            }

            @Override
            public void onError(int resMsg) {
                post(BugsEvent.ERROR_SERVER, resMsg);
            }
        });

    }

    @Override
    public void subscribeToCustomer() {
        mDatabase.subscribeToCustomer(new BugsEventListener() {
            @Override
            public void onDataChangeProject(List<Project> projectList) {

            }

            @Override
            public void onDataChange(List<Customer> customerList) {
                post(customerList, BugsEvent.SUCCESS);// result customers
            }

            @Override
            public void onDataDeveloper(List<Developer> developerList) {

            }

            @Override
            public void onError(int resMsg) {
                post(BugsEvent.ERROR_SERVER, resMsg);
            }
        });
    }

    @Override
    public void subscribeToDeveloper() {
        mDatabase.subscribeToDeveloper(new BugsEventListener() {
            @Override
            public void onDataChangeProject(List<Project> projectList) {

            }

            @Override
            public void onDataChange(List<Customer> customerList) {

            }

            @Override
            public void onDataDeveloper(List<Developer> developerList) {
                postDeveloper(developerList, BugsEvent.RESULT_DEVELOPER);// result developers
            }

            @Override
            public void onError(int resMsg) {

            }
        });
    }

    @Override
    public void unsubscribeToProject() {
        mDatabase.unsubscribeToProject();
    }

    @Override
    public void unsubscribeToCustomer() {
        mDatabase.unsubscribeToCustomer();
    }

    @Override
    public void unsubscribeToDeveloper() {
        mDatabase.unsubscribeToDeveloper();
    }

    @Override
    public void addBugs(Bugs bugs) {
        mDatabase.addBugs(bugs, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(BugsEvent.UPDATE_SUCCESS);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent, resMsg);
            }

            @Override
            public void onError() {

            }
        });

    }

    @Override
    public void updateBugs(Bugs bugs) {
        mDatabase.updateBugs(bugs, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(BugsEvent.UPDATE_SUCCESS);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent, resMsg);
            }

            @Override
            public void onError() {

            }
        });

    }

    private void post(int typeEvent) {
        post(typeEvent, 0);
    }

    private void post(int typeEvent, int resMsg) {
        BugsEvent event = new BugsEvent();
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }

    private void post(List<Customer> customers, int typeEvent){
        post(customers, typeEvent, 0); // result customers
    }

    private void postProject(List<Project> projects, int typeEvent){
        postProject(projects, typeEvent, 0); // result projects
    }

    private void postDeveloper(List<Developer> developers, int typeEvent){
        postDeveloper(developers, typeEvent, 0); // result customers
    }


    private void postProject(List<Project> projects, int typeEvent, int resMsg){
        BugsEvent event = new BugsEvent();
        event.setProjects(projects);
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }

    private void post(List<Customer> customers, int typeEvent, int resMsg){
        BugsEvent event = new BugsEvent();
        event.setCustomers(customers);
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }

    private void postDeveloper(List<Developer> developers, int typeEvent, int resMsg){
        BugsEvent event = new BugsEvent();
        event.setDevelopers(developers);
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }

}
