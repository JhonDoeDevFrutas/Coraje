package standardsoft.com.coraje.ui.view.promptTaskModule.model;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import standardsoft.com.coraje.common.BasicErrorEventCallback;
import standardsoft.com.coraje.data.model.entities.Customer;
import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.Project;
import standardsoft.com.coraje.data.model.entities.PromptTask;
import standardsoft.com.coraje.ui.view.promptTaskModule.events.PromptTaskEvent;
import standardsoft.com.coraje.ui.view.promptTaskModule.model.dataAccess.PromptTaskEventListener;
import standardsoft.com.coraje.ui.view.promptTaskModule.model.dataAccess.RealtimeDatabase;

public class PromptTaskInteractorClass implements PromptTaskInteractor {

    private RealtimeDatabase mDatabase;

    public PromptTaskInteractorClass() {
        mDatabase = new RealtimeDatabase();
    }

    @Override
    public void subscribeToProject() {
        mDatabase.subscribeToProject(new PromptTaskEventListener() {
            @Override
            public void onDataChangeProject(List<Project> projectList) {
                postProject(projectList, PromptTaskEvent.RESULT_PROJECT);// result projects
            }

            @Override
            public void onDataChange(List<Customer> customerList) {

            }

            @Override
            public void onDataDeveloper(List<Developer> developerList) {

            }

            @Override
            public void onError(int resMsg) {
                post(PromptTaskEvent.ERROR_SERVER, resMsg);
            }
        });
    }

    @Override
    public void subscribeToCustomer() {
        mDatabase.subscribeToCustomer(new PromptTaskEventListener() {
            @Override
            public void onDataChangeProject(List<Project> projectList) {

            }

            @Override
            public void onDataChange(List<Customer> customerList) {
                post(customerList, PromptTaskEvent.SUCCESS);// result customers
            }

            @Override
            public void onDataDeveloper(List<Developer> developerList) {

            }

            @Override
            public void onError(int resMsg) {
                post(PromptTaskEvent.ERROR_SERVER, resMsg);
            }
        });

    }

    @Override
    public void subscribeToDeveloper() {
        mDatabase.subscribeToDeveloper(new PromptTaskEventListener() {
            @Override
            public void onDataChangeProject(List<Project> projectList) {

            }

            @Override
            public void onDataChange(List<Customer> customerList) {

            }

            @Override
            public void onDataDeveloper(List<Developer> developerList) {
                postDeveloper(developerList, PromptTaskEvent.RESULT_DEVELOPER);// result developers
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
    public void addPromptTask(PromptTask promptTask) {
        mDatabase.addPromptTask(promptTask, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(PromptTaskEvent.UPDATE_SUCCESS);
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
    public void updatePromptTask(PromptTask promptTask) {
        mDatabase.updatePromptTask(promptTask, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(PromptTaskEvent.UPDATE_SUCCESS);
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
        PromptTaskEvent event = new PromptTaskEvent();
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
        PromptTaskEvent event = new PromptTaskEvent();
        event.setProjects(projects);
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }

    private void post(List<Customer> customers, int typeEvent, int resMsg){
        PromptTaskEvent event = new PromptTaskEvent();
        event.setCustomers(customers);
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }

    private void postDeveloper(List<Developer> developers, int typeEvent, int resMsg){
        PromptTaskEvent event = new PromptTaskEvent();
        event.setDevelopers(developers);
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }

}
