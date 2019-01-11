package standardsoft.com.coraje.ui.view.SignInModule.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rey.material.widget.CheckBox;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.User;
import standardsoft.com.coraje.data.preferences.SessionPrefs;
import standardsoft.com.coraje.ui.view.Home;
import standardsoft.com.coraje.ui.view.SignInModule.SignInPresenter;
import standardsoft.com.coraje.ui.view.SignInModule.SignInPresenterClass;
import standardsoft.com.coraje.utilies.CommonUtils;

public class SignInActivity extends AppCompatActivity implements SignInView{

    // Referencias UI
    EditText edtPhone, edtPassword;
    Button btnSignIn;
    CheckBox ckbRemember;

    private SignInPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mPresenter = new SignInPresenterClass(this);
        mPresenter.onCreate();

        // Preparar elementos UI
        prepararUI();
    }

    private void prepararUI() {
        edtPhone = (EditText)findViewById(R.id.edt_phone);
        edtPassword = (EditText)findViewById(R.id.edt_password);

        btnSignIn = (Button)findViewById(R.id.btn_signin);
        ckbRemember = (CheckBox)findViewById(R.id.ckbRemember) ;

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonUtils.validateSignIn(getBaseContext(), edtPhone, edtPassword)){
                    mPresenter.subscribeToUser(edtPhone.getText().toString(), edtPassword.getText().toString());
                }
            }
        });
    }

    /*SignInView*/
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void openHomeActivity(User user) {
        String phone = user.getMovil().toString();
        String name = user.getName().toString();
        // Save user & password
        if (ckbRemember.isChecked()){
            // Guardar usuario en preferencias
            SessionPrefs.get(SignInActivity.this).saveUser(phone, name);
        }

        Intent intentHome = new Intent(this, Home.class);
        intentHome.putExtra(User.NAME, name);
        intentHome.putExtra(User.PHONE, phone);
        startActivity(intentHome);
        finish();
    }


    @Override
    public void onShowError(int resMsg) {

    }
}
