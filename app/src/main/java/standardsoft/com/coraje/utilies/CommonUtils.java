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
}
