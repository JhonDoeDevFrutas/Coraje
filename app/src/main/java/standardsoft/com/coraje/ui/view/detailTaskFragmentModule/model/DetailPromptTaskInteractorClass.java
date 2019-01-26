package standardsoft.com.coraje.ui.view.detailTaskFragmentModule.model;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.PromptTask;
import standardsoft.com.coraje.ui.view.detailTaskFragmentModule.events.PromptTaskEvent;
import standardsoft.com.coraje.ui.view.detailTaskFragmentModule.model.dataAcces.DetailPromptTaskEventListener;
import standardsoft.com.coraje.ui.view.detailTaskFragmentModule.model.dataAcces.RealtimeDatabase;

public class DetailPromptTaskInteractorClass implements DetailPromptTaskInteractor {

    private RealtimeDatabase mDatabase;

    public DetailPromptTaskInteractorClass() {
        mDatabase = new RealtimeDatabase();
    }

    @Override
    public void subscribeToPromptTaskList(String phone, boolean filter) {
        mDatabase.subscribeToPromptTaskList(phone, filter, new DetailPromptTaskEventListener() {
            @Override
            public void onDataChange(List<PromptTask> promptTaskList) {
                post(PromptTaskEvent.PROMTP_TASK_ADDED, promptTaskList);
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
        mDatabase.subscribeToDeveloperList(new DetailPromptTaskEventListener() {
            @Override
            public void onDataChange(List<PromptTask> bugsList) {

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

    private void post(int typeEvent, List<PromptTask> promptTaskList){
        post(typeEvent, promptTaskList, 0);
    }

    private void post(int typeEvent, List<PromptTask> promptTaskList, int resMsg) {
        PromptTaskEvent event = new PromptTaskEvent();
        event.setTypeEvent(typeEvent);
        event.setPromptTaskList(promptTaskList);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }

    // result developer
    private void postDeveloper(List<Developer> developers, int typeEvent){
        PromptTaskEvent event = new PromptTaskEvent();
        event.setDevelopers(developers);
        event.setTypeEvent(typeEvent);
        EventBus.getDefault().post(event);
    }

}
