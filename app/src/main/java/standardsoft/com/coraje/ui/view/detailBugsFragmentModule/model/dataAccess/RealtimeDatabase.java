package standardsoft.com.coraje.ui.view.detailBugsFragmentModule.model.dataAccess;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.data_access.FirebaseRealtimeDatabaseAPI;
import standardsoft.com.coraje.data.model.entities.Bugs;
import standardsoft.com.coraje.data.model.entities.Developer;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase(){
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    /*
     *   public methods
     * */
    public void subscribeToBugsList(final String phone, final boolean filter, final BugsEventListener listener){
        Query myQueryBugs = mDatabaseAPI.getBugsReference().orderByChild(Bugs.DATE);
        myQueryBugs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Bugs> bugsList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Bugs bugs = getBugs(postSnapshot);
                    if (!filter){
                        String strPhone = bugs.getAssignee() != null ? bugs.getAssignee().getMovil() : "";
                        if (strPhone.equals(phone)){
                            bugsList.add(bugs);
                        }
                    }else {
                        bugsList.add(bugs);
                    }
                }

                Collections.sort(bugsList, Bugs.Comparators.DATE);
                listener.onDataChange(bugsList);
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

    public void subscribeToDeveloperList(final BugsEventListener listener) {
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

    private Bugs getBugs(DataSnapshot dataSnapshot){
        Bugs bugs = dataSnapshot.getValue(Bugs.class);
        if (bugs != null){
            bugs.setId(dataSnapshot.getKey());
        }
        return bugs;
    }

    private Developer getDeveloper(DataSnapshot dataSnapshot) {
        Developer developer = dataSnapshot.getValue(Developer.class);
        developer.setId(dataSnapshot.getKey());
        return developer;
    }
}
