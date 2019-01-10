package standardsoft.com.coraje.ui.view.detailTaskModule.model.dataAccess;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.common.BasicErrorEventCallback;
import standardsoft.com.coraje.data.model.data_access.FirebaseRealtimeDatabaseAPI;
import standardsoft.com.coraje.data.model.entities.Remark;
import standardsoft.com.coraje.data.model.entities.Status;
import standardsoft.com.coraje.data.model.entities.SubPlanning;
import standardsoft.com.coraje.data.preferences.FirebaseReferences;
import standardsoft.com.coraje.ui.view.detailTaskModule.events.DetailTaskEvent;
import standardsoft.com.coraje.ui.view.planningModule.events.PlanningEvent;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase() {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    /*
     *   public methods
     * */


    public void subscribeToRemarkList(String idSubPlanning, final DetailEventListener listener) {
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

    public void addRemarkTask(final String idPlanning, final String idSubPlanning, Remark remark, final BasicErrorEventCallback callback) {

/*
        Map<String, String> remarkRequestMap = new HashMap<>();
        remarkRequestMap.put(Remark.DESCRIPTION, remark.getDescription());
        remarkRequestMap.put(Remark.DATE, Long.toString(remark.getDate())); //Convert long to string

        Map<String, Object> request = new HashMap<>();
        request.put(FirebaseReferences.REMARK_REFERENCE, remarkRequestMap);

        mDatabaseAPI.getRemarkReference(idSubPlanning).updateChildren(request, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null){
                    callback.onSuccess();
                } else {
                    callback.onError();
                }
            }
        });
*/

        mDatabaseAPI.getRemarkReference(idSubPlanning).push().setValue(remark, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    /*get subPlanning for update status*/
                    getSubPlanning(idPlanning, idSubPlanning);

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

    private void getSubPlanning(final String idPlanning, String idSubPlanning) {
        mDatabaseAPI.getSubPlanningReference(idPlanning, idSubPlanning).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SubPlanning subPlanning = getSubPlanning(dataSnapshot);
                subPlanning.setId(dataSnapshot.getKey());
                if (subPlanning.getStatus().getDescription().equals("ESPERANDO REVISION")) {
                    subPlanning.setStatus(Status.getStatus("EN PROCESO"));

                    updateSubPlanning(idPlanning, subPlanning); //update subplanning status
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateSubPlanning(String idPlanning, SubPlanning subPlanning) {
        mDatabaseAPI.getSubPlanningReference(idPlanning, subPlanning.getId()).setValue(subPlanning)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    private SubPlanning getSubPlanning(DataSnapshot dataSnapshot) {
        SubPlanning subPlanning = dataSnapshot.getValue(SubPlanning.class);
        return subPlanning;
    }


}
