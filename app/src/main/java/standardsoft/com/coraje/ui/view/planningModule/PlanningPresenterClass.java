package standardsoft.com.coraje.ui.view.planningModule;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import standardsoft.com.coraje.data.model.entities.Planning;
import standardsoft.com.coraje.ui.view.planningModule.events.PlanningEvent;
import standardsoft.com.coraje.ui.view.planningModule.model.PlanningInteractor;
import standardsoft.com.coraje.ui.view.planningModule.model.PlanningInteractorClass;
import standardsoft.com.coraje.ui.view.planningModule.view.PlanningView;

/**
    ¿De que debería estar libre nuestro Presenter?
        Del framework de android.
        De conexiones directas a la base de datos
 */
public class PlanningPresenterClass implements PlanningPresenter {

   private PlanningView mView;
   private PlanningInteractor mInteractor;

    public PlanningPresenterClass(PlanningView mView) {
        this.mView = mView;
        this.mInteractor = new PlanningInteractorClass();
    }

    @Override
    public void onCreate() {
      EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        if (setProgress()){
            mInteractor.onResume();
        }
    }

    @Override
    public void onPause() {
        if (setProgress()){
            mInteractor.onPause();
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
        EventBus.getDefault().unregister(this);
    }

   @Override
   public void addPlanning(Planning planning) {
     mInteractor.addPlanning(planning);
   }

    private boolean setProgress(){
        if (mView != null){
            mView.showProgress();
            return true;
        }
        return false;
    }

    @Subscribe
    @Override
    public void onEventListener(PlanningEvent event) {
        if (mView != null){
            mView.hideProgress();

            switch (event.getTypeEvent()){
                case PlanningEvent.SUCCESS_ADD:
                    if (setProgress()){

                    }
                case PlanningEvent.ERROR_SERVER:
                    mView.onShowError(event.getResMsg());
                    break;
            }
        }
    }

}
