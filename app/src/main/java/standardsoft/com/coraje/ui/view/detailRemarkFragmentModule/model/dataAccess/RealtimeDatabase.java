package standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.model.dataAccess;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.data_access.FirebaseRealtimeDatabaseAPI;
import standardsoft.com.coraje.data.model.entities.Remark;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase() {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    /*
     *   public methods
     * */
    public void subscribeToRemarkList(final DetailRemarkEventListener listener) {
        mDatabaseAPI.getRemarkReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Remark> remarks = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot : postSnapshot.getChildren()) {
                        remarks.add(getRemark(snapshot));
                    }
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

    private Remark getRemark(DataSnapshot dataSnapshot) {
        Remark remark = dataSnapshot.getValue(Remark.class);
        return remark;
    }
}
