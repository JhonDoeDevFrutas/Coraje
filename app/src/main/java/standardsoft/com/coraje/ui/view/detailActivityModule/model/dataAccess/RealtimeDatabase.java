package standardsoft.com.coraje.ui.view.detailActivityModule.model.dataAccess;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.data_access.FirebaseRealtimeDatabaseAPI;
import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.SubPlanning;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase(){
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void subscribeToSubPlanningList(final String phone, final boolean selectAll, final ActivitysEventListener listener){
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

                        if (!selectAll){
                            String strPhone = subPlanning.getAssignee() != null ? subPlanning.getAssignee().getMovil() : "";
                            if (strPhone.equals(phone)){
                                //adding subplanning to the list
                                subPlannings.add(subPlanning);
                            }
                        }else {
                            //adding subplanning to the list
                            subPlannings.add(subPlanning);

                        }
                    }
                }

                Collections.sort(subPlannings, SubPlanning.Comparators.DATE);
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

    public void subscribeToDeveloperList(final ActivitysEventListener listener) {
        mDatabaseAPI.getUserReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Developer> developerList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    developerList.add(getDeveloper(postSnapshot));
                }
                Collections.sort(developerList, Developer.Comparators.name);
                listener.onDataDeveloper(developerList);
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


        public void getTaskByDeveloper(final ActivitysEventListener listener){
        Query myQuery = mDatabaseAPI.getSubPlanningReference().  orderByChild("name").equalTo("Danny Murcia");
        myQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<SubPlanning> subPlannings = new ArrayList<>();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String idPlanning = postSnapshot.getKey();
                    for (DataSnapshot snapshot : postSnapshot.getChildren()) {
                        // getting subplanning
                        SubPlanning subPlanning = snapshot.getValue(SubPlanning.class);
                        subPlanning.setId(snapshot.getKey());
                        subPlanning.setIdPlanning(idPlanning);
                        //adding subplanning to the list
                        //subPlannings.add(getSubPlanning(snapshot));
                        subPlannings.add(subPlanning);
                    }
                }

                Collections.sort(subPlannings, SubPlanning.Comparators.DATE);
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

    private Developer getDeveloper(DataSnapshot dataSnapshot) {
        Developer developer = dataSnapshot.getValue(Developer.class);
        developer.setId(dataSnapshot.getKey());
        return developer;
    }
}

