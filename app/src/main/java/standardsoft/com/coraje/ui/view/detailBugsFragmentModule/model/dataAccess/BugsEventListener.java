package standardsoft.com.coraje.ui.view.detailBugsFragmentModule.model.dataAccess;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Bugs;
import standardsoft.com.coraje.data.model.entities.Developer;

public interface BugsEventListener {
    void onDataChange(List<Bugs> bugsList);
    void onDataDeveloper(List<Developer> developerList);

    void onError(int resMsg);
}
