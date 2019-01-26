package standardsoft.com.coraje.ui.view.detailTaskFragmentModule.model.dataAcces;

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
import standardsoft.com.coraje.data.model.entities.PromptTask;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase(){
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    /*
     *   public methods
     * */
    public void subscribeToPromptTaskList(final String phone, final boolean filter, final DetailPromptTaskEventListener listener){
        Query myQueryPromptTask = mDatabaseAPI.getPromptTaskReference().orderByChild(PromptTask.DATE);
        myQueryPromptTask.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<PromptTask> promptTaskList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    PromptTask promptTask = getPromptTask(postSnapshot);
                    if (!filter){
                        String strPhone = promptTask.getAssignee() != null ? promptTask.getAssignee().getMovil() : "";
                        if (strPhone.equals(phone)){
                            promptTaskList.add(promptTask);
                        }
                    }else {
                        promptTaskList.add(promptTask);
                    }
                }

                Collections.sort(promptTaskList, PromptTask.Comparators.DATE);
                listener.onDataChange(promptTaskList);
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

    public void subscribeToDeveloperList(final DetailPromptTaskEventListener listener) {
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

    private PromptTask getPromptTask(DataSnapshot dataSnapshot){
        PromptTask promptTask = dataSnapshot.getValue(PromptTask.class);
        if (promptTask != null){
            promptTask.setId(dataSnapshot.getKey());
        }
        return promptTask;
    }

    private Developer getDeveloper(DataSnapshot dataSnapshot) {
        Developer developer = dataSnapshot.getValue(Developer.class);
        developer.setId(dataSnapshot.getKey());
        return developer;
    }

}
