package standardsoft.com.coraje.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import static android.content.Context.MODE_PRIVATE;

public class SessionPrefs {

    public static final String NAME = "name";

    public static final String PHONE = "phone";
    public static final String PWD_KEY = "password";

    private final SharedPreferences mPrefs;

    private boolean mIsLoggedIn = false;

    private static SessionPrefs INSTANCE;

    private SessionPrefs(Context context){
        mPrefs = context.getApplicationContext().getSharedPreferences(NAME, MODE_PRIVATE);

        mIsLoggedIn = !TextUtils.isEmpty(mPrefs.getString(PHONE, null));
    }

    public static SessionPrefs get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SessionPrefs(context);
        }
        return INSTANCE;
    }

    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    public void saveUser(String phone, String name){
        SharedPreferences.Editor editor = mPrefs.edit();

        editor.putString(PHONE, phone);
        editor.putString(NAME, name);
        editor.apply();

        mIsLoggedIn = true;
    }

    public void logOut(){
        mIsLoggedIn = false;

        SharedPreferences.Editor editor = mPrefs.edit();
        editor.remove(PHONE);
        editor.remove(PWD_KEY);
        editor.clear();
        editor.apply();
    }

    public String getName(){
        return mPrefs.getString(NAME, null);
    }

    public String getPhone(){
        return mPrefs.getString(PHONE, null);
    }

}
