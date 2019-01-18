package standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.model.dataAccess;

import java.util.List;

import standardsoft.com.coraje.data.model.entities.Remark;

public interface DetailRemarkEventListener {
    void onDataChange(List<Remark> remarkList);

    void onError(int resMsg);
}
