package standardsoft.com.coraje.utilies;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.EditText;

import standardsoft.com.coraje.R;

public class CommonUtils {
    public static boolean validatePlanning(Context context, EditText edtTask){
        boolean isValid = true;

        if (edtTask.getText().toString().trim().isEmpty()){
            edtTask.setError(context.getString(R.string.common_validate_field_required));
            edtTask.requestFocus();
            isValid = false;
        }

        return isValid;
    }

    public static boolean validateUser(Context context, EditText edtName, EditText edtPhone, EditText edtPassword){
        boolean isValid = true;

        String name  = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (name.isEmpty()){
            edtName.setError(context.getString(R.string.common_validate_field_required));
            edtName.requestFocus();
            isValid = false;
        }else if (phone.isEmpty()){
            edtPhone.setError(context.getString(R.string.common_validate_field_required));
            edtPhone.requestFocus();
            isValid = false;
        }else if (password.isEmpty()){
            edtPassword.setError(context.getString(R.string.common_validate_field_required));
            edtPassword.requestFocus();
            isValid = false;
        }

        return isValid;
    }

    public static boolean validateSignIn(Context context, EditText edtPhone, EditText edtPassword){
        boolean isValid = true;

        String phone = edtPhone.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (phone.isEmpty()){
            edtPhone.setError(context.getString(R.string.common_validate_field_required));
            edtPhone.requestFocus();
            isValid = false;
        }else if (password.isEmpty()){
            edtPassword.setError(context.getString(R.string.common_validate_field_required));
            edtPassword.requestFocus();
            isValid = false;
        }

        return isValid;
    }
}
