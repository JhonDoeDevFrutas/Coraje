package standardsoft.com.coraje.data.model.data_access;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import standardsoft.com.coraje.data.preferences.FirebaseReferences;

public class FirebaseRealtimeDatabaseAPI {
    private static final String PATH_CUSTOMERS  = "customers";

    private static final String PATH_PLANNING   = "planning";
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

    public DatabaseReference getCustomersReference(){
        return getReference().child(PATH_CUSTOMERS);
    }

    public DatabaseReference getProjectsReference(){
        return getReference().child(FirebaseReferences.PROJECT_REFERENCE);
    }

    public DatabaseReference getPlanningReference(){
        return getReference().child(PATH_PLANNING);
    }

    public DatabaseReference getSupportReference(){
        return getSupportReference().child(PATH_SUPPORT);
    }

    public DatabaseReference getBugsReference(){
        return getBugsReference().child(PATH_BUGS);
    }


}
