package standardsoft.com.coraje.ui.view.detailBugsFragmentModule.model;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Bugs;
import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.ui.view.detailBugsFragmentModule.events.DetailBugsEvent;
import standardsoft.com.coraje.ui.view.detailBugsFragmentModule.model.dataAccess.BugsEventListener;
import standardsoft.com.coraje.ui.view.detailBugsFragmentModule.model.dataAccess.RealtimeDatabase;

public class BugsInteractorClass implements BugsInteractor {

    private RealtimeDatabase mDatabase;

    public BugsInteractorClass() {
        mDatabase = new RealtimeDatabase();
    }

    @Override
    public void subscribeToBugsList(String phone, boolean filter) {
        mDatabase.subscribeToBugsList(phone, filter, new BugsEventListener() {
            @Override
            public void onDataChange(List<Bugs> bugsList) {
                post(DetailBugsEvent.BUGS_ADDED, bugsList);
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
        mDatabase.subscribeToDeveloperList(new BugsEventListener() {
            @Override
            public void onDataChange(List<Bugs> bugsList) {

            }

            @Override
            public void onDataDeveloper(List<Developer> developerList) {
                postDeveloper(developerList, DetailBugsEvent.RESULT_DEVELOPER);// result developers
            }

            @Override
            public void onError(int resMsg) {

            }
        });

    }

    @Override
    public void unsubscribeToBugsList() {

    }

    private void post(int typeEvent, List<Bugs> bugsList){
        post(typeEvent, bugsList, 0);
    }

    private void post(int typeEvent, List<Bugs> bugsList, int resMsg) {
        DetailBugsEvent event = new DetailBugsEvent();
        event.setTypeEvent(typeEvent);
        event.setBugsList(bugsList);
        event.setResMsg(resMsg);
        EventBus.getDefault().post(event);
    }

    // result developer
    private void postDeveloper(List<Developer> developers, int typeEvent){
        DetailBugsEvent event = new DetailBugsEvent();
        event.setDevelopers(developers);
        event.setTypeEvent(typeEvent);
        EventBus.getDefault().post(event);
    }

}
