package standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.model.dataAccess;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.data_access.FirebaseRealtimeDatabaseAPI;
import standardsoft.com.coraje.data.model.entities.Remark;
import standardsoft.com.coraje.data.model.entities.SubPlanning;

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
                    String idSubPlanning = postSnapshot.getKey();
                    for (DataSnapshot snapshot : postSnapshot.getChildren()) {
                        Remark remarkTmp = getRemark(snapshot);
                        remarkTmp.setIdSubPlanning(idSubPlanning);
                        remarks.add(remarkTmp);
                    }
                }
                Collections.sort(remarks, Remark.Comparators.DATE);
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

    public void subscribeToSubPlanningList(final DetailRemarkEventListener listener) {
        mDatabaseAPI.getSubPlanningReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<SubPlanning> subPlannings = new ArrayList<>();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String idPlanning = postSnapshot.getKey();
                    for (DataSnapshot snapshot : postSnapshot.getChildren()) {
                        // getting subplanning
                        SubPlanning subPlanning = getSubPlanning(snapshot);
                        subPlanning.setIdPlanning(idPlanning);

                        //adding subplanning to the list
                        subPlannings.add(subPlanning);
                    }
                }

                listener.onDataSubPlanning(subPlannings);

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
        remark.setId(dataSnapshot.getKey());
        return remark;
    }

    private SubPlanning getSubPlanning(DataSnapshot dataSnapshot) {
        SubPlanning subPlanning = dataSnapshot.getValue(SubPlanning.class);
        if (subPlanning != null) {
            subPlanning.setId(dataSnapshot.getKey());
        }
        return subPlanning;
    }

}
