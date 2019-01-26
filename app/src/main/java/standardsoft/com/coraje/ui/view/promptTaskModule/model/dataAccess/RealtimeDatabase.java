package standardsoft.com.coraje.ui.view.promptTaskModule.model.dataAccess;

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
import standardsoft.com.coraje.data.model.entities.Customer;
import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.Project;
import standardsoft.com.coraje.data.model.entities.PromptTask;
import standardsoft.com.coraje.ui.view.promptTaskModule.events.PromptTaskEvent;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;
    private ValueEventListener mPromptTaskValueEventListener;

    public RealtimeDatabase() {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void addPromptTask(PromptTask promptTask, final BasicErrorEventCallback callback) {
        mDatabaseAPI.getPromptTaskReference().push().setValue(promptTask, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    callback.onSuccess();
                } else {
                    switch (databaseError.getCode()) {
                        default:
                            callback.onError(PromptTaskEvent.ERROR_SERVER, 0);
                    }
                }
            }
        });
    }

    public void  updatePromptTask(PromptTask promptTask, final BasicErrorEventCallback callback){
        mDatabaseAPI.getPromptTaskReference().child(promptTask.getId()).setValue(promptTask)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(PromptTaskEvent.ERROR_SERVER, 0);
                    }
                });
    }

    public void subscribeToProject(final PromptTaskEventListener listener) {
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

    public void subscribeToCustomer(final PromptTaskEventListener listener) {
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

    public void subscribeToDeveloper(final PromptTaskEventListener listener) {
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
        if (mPromptTaskValueEventListener != null){
            mDatabaseAPI.getCustomersReference().removeEventListener(mPromptTaskValueEventListener);
        }
    }
    public void unsubscribeToProject(){
        if (mPromptTaskValueEventListener != null){
            mDatabaseAPI.getProjectsReference().removeEventListener(mPromptTaskValueEventListener);
        }
    }
    public void unsubscribeToDeveloper(){
        if (mPromptTaskValueEventListener != null){
            mDatabaseAPI.getDevelopersReference().removeEventListener(mPromptTaskValueEventListener);
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
