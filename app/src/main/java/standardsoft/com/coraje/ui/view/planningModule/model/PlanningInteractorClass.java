package standardsoft.com.coraje.ui.view.planningModule.model;

import standardsoft.com.coraje.data.model.entities.Planning;
import standardsoft.com.coraje.ui.view.planningModule.model.dataAccess.RealtimeDatabase;

public class PlanningInteractorClass implements PlanningInteractor {

    private RealtimeDatabase mDatabase;

    public PlanningInteractorClass() {
        mDatabase = new RealtimeDatabase();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void addPlanning(Planning planning) {
        mDatabase.addPlanning(planning);
    }
}
