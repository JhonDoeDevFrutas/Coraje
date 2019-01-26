package standardsoft.com.coraje.ui.view.detailActivityModule.model.dataAccess;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.SubPlanning;

public interface ActivitysEventListener {
    void onDataChange(List<SubPlanning> subPlanningList);
    void onDataDeveloper(List<Developer> developerList);

    void onError(int resMsg);
}
