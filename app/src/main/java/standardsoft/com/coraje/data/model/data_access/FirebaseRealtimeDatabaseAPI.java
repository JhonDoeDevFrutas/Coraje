package standardsoft.com.coraje.data.model.data_access;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import standardsoft.com.coraje.data.preferences.FirebaseReferences;

public class FirebaseRealtimeDatabaseAPI {
    private static final String PATH_CUSTOMERS  = "customers";

    private static final String PATH_SUPPORT    = "support";
    private static final String PATH_BUGS       = "bugs";

    private DatabaseReference mReference;

    private static FirebaseRealtimeDatabaseAPI INSTANCE = null;

    private FirebaseRealtimeDatabaseAPI() {
        mReference = FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseRealtimeDatabaseAPI getInstance(){
        if (INSTANCE == null){
            INSTANCE = new FirebaseRealtimeDatabaseAPI();
        }
        return INSTANCE;
    }

    // referencias
    public DatabaseReference getReference(){
        return mReference;
    }

    public DatabaseReference getRootReference(){
        return mReference.getRoot();
    }

    public DatabaseReference getCustomersReference(){
        return getReference().child(PATH_CUSTOMERS);
    }

    public DatabaseReference getProjectsReference(){
        return getReference().child(FirebaseReferences.PROJECT_REFERENCE);
    }
    public DatabaseReference getDevelopersReference(){
        return getReference().child(FirebaseReferences.DEVELOPER_REFERENCE);
    }

    public DatabaseReference getPlanningReference(){
        return getReference().child(FirebaseReferences.PLANNING_REFERENCE);
    }

    public DatabaseReference getPlanningReference(String uid){
        return getReference().child(FirebaseReferences.PLANNING_REFERENCE).child(uid);
    }

    public DatabaseReference getSubPlanningReference(){
        return getReference().child(FirebaseReferences.SUBPLANNING_REFERENCE);
    }

    public DatabaseReference getSubPlanningReference(String uid){
        return getReference().child(FirebaseReferences.SUBPLANNING_REFERENCE).child(uid);
    }

    public DatabaseReference getSubPlanningReference(String idPlanning, String uid){
        return getReference().child(FirebaseReferences.SUBPLANNING_REFERENCE).child(idPlanning).child(uid);
    }

    public DatabaseReference getRemarkReference(){
        return getReference().child(FirebaseReferences.REMARK_REFERENCE);
    }
    public DatabaseReference getRemarkReference(String uid){
        return getReference().child(FirebaseReferences.REMARK_REFERENCE).child(uid);
    }

    public DatabaseReference getSupportReference(){
        return getSupportReference().child(PATH_SUPPORT);
    }

    public DatabaseReference getBugsReference(){
        return getBugsReference().child(PATH_BUGS);
    }


}
