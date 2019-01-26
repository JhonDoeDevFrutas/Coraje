package standardsoft.com.coraje.ui.view.bugsModule;

import standardsoft.com.coraje.data.model.entities.Bugs;
import standardsoft.com.coraje.ui.view.bugsModule.events.BugsEvent;

public interface BugsPresenter {
    void onCreate();
    void onResume();
    void onPause();
    void onDestroy();

    void addBugs(Bugs bugs);
    void updateBugs(Bugs bugs);

    void onEventListener(BugsEvent event);
}
