package standardsoft.com.coraje.ui.view.detailActivityModule.view;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.SubPlanning;

public interface ActivitysView {

    void showProgress();
    void hideProgress();

    void resultSubPlanning(List<SubPlanning> datas);
    void requestDeveloper(List<Developer> developersDatas);

    void showError(int resMsg);
}
