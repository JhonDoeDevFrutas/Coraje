package standardsoft.com.coraje.ui.view.detailActivityModule.model.dataAccess;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.data_access.FirebaseRealtimeDatabaseAPI;
import standardsoft.com.coraje.data.model.entities.SubPlanning;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase(){
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void subscribeToSubPlanningList(final ActivitysEventListener listener){
        mDatabaseAPI.getSubPlanningReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<SubPlanning> subPlannings = new ArrayList<>();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot : postSnapshot.getChildren()) {
                        // getting subplanning
                        SubPlanning subPlanning = snapshot.getValue(SubPlanning.class);
                        //adding subplanning to the list
                        subPlannings.add(getSubPlanning(snapshot));
                    }
                }

                listener.onDataChange(subPlannings);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                switch (databaseError.getCode()){
                    case DatabaseError.PERMISSION_DENIED:
                        listener.onError(R.string.error_permission_denied);
                        break;
                    default:
                        listener.onError(R.string.error_server);
                }
            }
        });
    }

    private SubPlanning getSubPlanning(DataSnapshot dataSnapshot){
        SubPlanning subPlanning = dataSnapshot.getValue(SubPlanning.class);
        if (subPlanning != null){
            subPlanning.setId(dataSnapshot.getKey());
        }
        return subPlanning;
    }

}

