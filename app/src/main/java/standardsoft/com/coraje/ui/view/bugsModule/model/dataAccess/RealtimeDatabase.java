package standardsoft.com.coraje.ui.view.bugsModule.model.dataAccess;

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
import standardsoft.com.coraje.data.model.entities.Bugs;
import standardsoft.com.coraje.data.model.entities.Customer;
import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.Project;
import standardsoft.com.coraje.ui.view.bugsModule.events.BugsEvent;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;
    private ValueEventListener mBugsValueEventListener;

    public RealtimeDatabase() {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void addBugs(Bugs bugs, final BasicErrorEventCallback callback) {
        mDatabaseAPI.getBugsReference().push().setValue(bugs, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    callback.onSuccess();
                } else {
                    switch (databaseError.getCode()) {
                        default:
                            callback.onError(BugsEvent.ERROR_SERVER, 0);
                    }
                }
            }
        });
    }

    public void  updateBugs(Bugs bugs, final BasicErrorEventCallback callback){
        mDatabaseAPI.getBugsReference().child(bugs.getId()).setValue(bugs)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(BugsEvent.ERROR_SERVER, 0);
                    }
                });
    }


    public void subscribeToProject(final BugsEventListener listener) {
        mDatabaseAPI.getProjectsReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Project> projectsList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    projectsList.add(getProject(postSnapshot));
                }

                Collections.sort(projectsList, Project.Comparators.DESCRIPTION);
                listener.onDataChangeProject(projectsList);
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

    public void subscribeToCustomer(final BugsEventListener listener) {
        mDatabaseAPI.getCustomersReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Customer> customerList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    customerList.add(getCustomer(postSnapshot));
                }

                Collections.sort(customerList, Customer.Comparators.NAME); // order by name
                listener.onDataChange(customerList);
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

    public void subscribeToDeveloper(final BugsEventListener listener) {
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


    public void unsubscribeToCustomer(){
        if (mBugsValueEventListener != null){
            mDatabaseAPI.getCustomersReference().removeEventListener(mBugsValueEventListener);
        }
    }
    public void unsubscribeToProject(){
        if (mBugsValueEventListener != null){
            mDatabaseAPI.getProjectsReference().removeEventListener(mBugsValueEventListener);
        }
    }
    public void unsubscribeToDeveloper(){
        if (mBugsValueEventListener != null){
            mDatabaseAPI.getDevelopersReference().removeEventListener(mBugsValueEventListener);
        }
    }

    private Customer getCustomer(DataSnapshot dataSnapshot) {
        Customer customer = dataSnapshot.getValue(Customer.class);

        return customer;
    }
    private Developer getDeveloper(DataSnapshot dataSnapshot) {
        Developer developer = dataSnapshot.getValue(Developer.class);

        return developer;
    }

    private Project getProject(DataSnapshot dataSnapshot) {
        Project project = dataSnapshot.getValue(Project.class);

        return project;
    }

}
