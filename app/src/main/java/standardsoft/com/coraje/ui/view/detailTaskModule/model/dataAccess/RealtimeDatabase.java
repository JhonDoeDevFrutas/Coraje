package standardsoft.com.coraje.ui.view.detailTaskModule.model.dataAccess;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import standardsoft.com.coraje.R;
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
    public void subscribeToRemarkList(String idSubPlanning, final DetailEventListener listener){
        mDatabaseAPI.getRemarkReference().child(idSubPlanning).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Remark> remarks = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    remarks.add(getRemark(postSnapshot));
                }
                listener.onDataChange(remarks);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                switch (databaseError.getCode()) {
                    case DatabaseError.PERMISSION_DENIED:
                        listener.onError(R.string.error_permission_denied);
                        break;
                    default:
                        listener.onError(R.string.error_server);
                }
            }
        });
    }

    public void addRemarkTask(String idSubPlanning, Remark remark,final BasicErrorEventCallback callback){
        mDatabaseAPI.getRemarkReference(idSubPlanning).push().setValue(remark, new DatabaseReference.CompletionListener() {
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

    private Remark getRemark(DataSnapshot dataSnapshot) {
        Remark remark = dataSnapshot.getValue(Remark.class);
        return remark;
    }


}
