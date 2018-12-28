package standardsoft.com.coraje.ui.view.detailTaskModule.model.dataAccess;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Remark;

public interface DetailEventListener {
    void onDataChange(List<Remark> remarkList);

    void onError(int resMsg);
}
