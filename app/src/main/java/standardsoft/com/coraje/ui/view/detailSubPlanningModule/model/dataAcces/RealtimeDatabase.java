package standardsoft.com.coraje.ui.view.detailSubPlanningModule.model.dataAcces;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.common.BasicErrorEventCallback;
import standardsoft.com.coraje.data.model.data_access.FirebaseRealtimeDatabaseAPI;
import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.Planning;
import standardsoft.com.coraje.data.model.entities.SubPlanning;
import standardsoft.com.coraje.data.model.entities.User;
import standardsoft.com.coraje.ui.view.detailSubPlanningModule.events.SubPlanningEvent;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase() {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    /*
     *   public methods
     * */
    public void subscribeToSubPlanningList(String idPlanning, final SubPlanningEventListener listener) {
        mDatabaseAPI.getSubPlanningReference().child(idPlanning).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<SubPlanning> subPlannings = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    subPlannings.add(getSubPlanning(postSnapshot));
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

    public void addSubPlanning(Planning planning, final BasicErrorEventCallback callback) {
        mDatabaseAPI.getPlanningReference().child(planning.getId()).setValue(planning)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(SubPlanningEvent.ERROR_SERVER, 0);
                    }
                });

    }

    public void addSubPlanning(String idPlanning, SubPlanning subPlanning, final BasicErrorEventCallback callback) {
        /*String id = mDatabaseAPI.getReference().push().getKey();
        subPlanning.setId(id);

        Map<String, String> planningRequestMap = new HashMap<>();
        planningRequestMap.put(SubPlanning.ID, subPlanning.getId());
        planningRequestMap.put(SubPlanning.TASK, subPlanning.getTask());
        planningRequestMap.put(SubPlanning.ASSIGNEE, subPlanning.getAssignee().getName());
        planningRequestMap.put(SubPlanning.STATUS, subPlanning.getStatus().getDescription());

        Map<String, String> myPlanningMap = new HashMap<>();
        myPlanningMap.put(Planning.TASK, planning.getTask());

        Map<String, Object> acceptRequest = new HashMap<>();
        acceptRequest.put(FirebaseReferences.PLANNING_REFERENCE + "/" + planning.getId() + "/" +
                FirebaseReferences.SUBPLANNING_REFERENCE + "/" + subPlanning.getId(), myPlanningMap);

        acceptRequest.put(FirebaseReferences.PLANNING_REFERENCE + "/" + subPlanning.getId() + "/" +
                FirebaseReferences.SUBPLANNING_REFERENCE + "/" + planning.getId(), planningRequestMap);

        acceptRequest.put(FirebaseReferences.PATH_REQUESTS + "/" + planning.getTask() + "/" + subPlanning.getId(), null);

        mDatabaseAPI.getRootReference().updateChildren(acceptRequest, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    callback.onSuccess();
                } else {
                    callback.onError();
                }
            }
        });*/

        mDatabaseAPI.getSubPlanningReference(idPlanning).push().setValue(subPlanning, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    callback.onSuccess();
                } else {
                    switch (databaseError.getCode()) {
                        default:
                            callback.onError(SubPlanningEvent.ERROR_SERVER, 0);
                    }
                }
            }
        });

/*
        mDatabaseAPI.getPlanningReference().child(idPlanning).child(FirebaseReferences.SUBPLANNING_REFERENCE).push().setValue(subPlanning, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    callback.onSuccess();
                } else {
                    switch (databaseError.getCode()) {
                        default:
                            callback.onError(SubPlanningEvent.ERROR_SERVER, 0);
                    }
                }
            }
        });
*/
    }

/*
    public void addSubPlanning(String idPlanning, SubPlanning subPlanning, final BasicErrorEventCallback callback) {
        mDatabaseAPI.getSubPlanningReference(idPlanning).push().setValue(subPlanning, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    callback.onSuccess();
                } else {
                    switch (databaseError.getCode()) {
                        default:
                            callback.onError(SubPlanningEvent.ERROR_SERVER, 0);
                    }
                }
            }
        });
    }
*/

    public void subscribeToDeveloperList(final SubPlanningEventListener listener) {
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

/*        mDatabaseAPI.getDevelopersReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Developer> developerList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    developerList.add(getDeveloper(postSnapshot));
                }
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
        });*/
    }

    private SubPlanning getSubPlanning(DataSnapshot dataSnapshot) {
        SubPlanning subPlanning = dataSnapshot.getValue(SubPlanning.class);
        return subPlanning;
    }

    private Developer getDeveloper(DataSnapshot dataSnapshot) {
        Developer developer = dataSnapshot.getValue(Developer.class);
        developer.setId(dataSnapshot.getKey());
        return developer;
    }
}
