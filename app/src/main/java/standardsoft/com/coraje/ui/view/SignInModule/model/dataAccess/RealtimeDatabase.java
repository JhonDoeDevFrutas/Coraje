package standardsoft.com.coraje.ui.view.SignInModule.model.dataAccess;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import standardsoft.com.coraje.data.model.data_access.FirebaseRealtimeDatabaseAPI;
import standardsoft.com.coraje.data.model.entities.User;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase() {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void subscribeToUser(final String phone, final String password, final SignInEventListener listener){
        mDatabaseAPI.getUserReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Check if user not exist in database
                if (dataSnapshot.child(phone).exists()){
                    // Get user information
                    User user = dataSnapshot.child(phone).getValue(User.class);

                    if (user.getPassword().equals(password)){
                        listener.onSuccess(user);
                        //Sign in succesfully
                    }else{
                        //Wrong Password!!!
                        listener.onError();
                    }
                }else {
                    //User not exist in database!!!
                    listener.onError();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
