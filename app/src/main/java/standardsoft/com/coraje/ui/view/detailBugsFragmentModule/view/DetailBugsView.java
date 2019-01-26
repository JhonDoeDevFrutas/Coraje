package standardsoft.com.coraje.ui.view.detailBugsFragmentModule.view;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Bugs;
import standardsoft.com.coraje.data.model.entities.Developer;

public interface DetailBugsView {
    void showProgress();
    void hideProgress();

    void requestDatas(List<Bugs> datas);
    void requestDeveloper(List<Developer> developersDatas);

    void showError(int resMsg);
}
