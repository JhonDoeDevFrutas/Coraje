package standardsoft.com.coraje.ui.view.detailTaskModule.model.dataAccess;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import standardsoft.com.coraje.common.BasicErrorEventCallback;
import standardsoft.com.coraje.data.model.data_access.FirebaseRealtimeDatabaseAPI;
import standardsoft.com.coraje.data.model.entities.Remark;
import standardsoft.com.coraje.ui.view.detailTaskModule.events.DetailTaskEvent;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase() {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    /*
     *   public methods
     * */
    public void addRemarkTask(String idSubPlanning, Remark remark,final BasicErrorEventCallback callback){
        mDatabaseAPI.getNotesReference(idSubPlanning).push().setValue(remark, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    callback.onSuccess();
                } else {
                    switch (databaseError.getCode()) {
                        default:
                            callback.onError(DetailTaskEvent.ERROR_SERVER, 0);
                    }
                }

            }
        });

    }

}
