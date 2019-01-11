package standardsoft.com.coraje.ui.view.SignUp.view;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.User;
import standardsoft.com.coraje.ui.view.Home;
import standardsoft.com.coraje.ui.view.SignUp.SignUpPresenter;
import standardsoft.com.coraje.ui.view.SignUp.SignUpPresenterClass;
import standardsoft.com.coraje.utilies.CommonUtils;

public class SignUpActivity extends AppCompatActivity implements SignUpView{

    // Referencias UI
    EditText edtPhone, edtName, edtPassword;
    Button btnSignUp;

    private SignUpPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mPresenter = new SignUpPresenterClass(this);
        mPresenter.onCreate();

        // Preparar elementos UI
        prepararUI();
    }

    private void prepararUI() {
        edtName = (EditText)findViewById(R.id.edt_name);
        edtPhone = (EditText)findViewById(R.id.edt_phone);
        edtPassword = (EditText)findViewById(R.id.edt_password);

        btnSignUp = (Button)findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonUtils.validateUser(getBaseContext(), edtName, edtPhone, edtPassword)){
                    User user = new User(edtName.getText().toString(),edtPhone.getText().toString(),
                            edtPassword.getText().toString());
                    mPresenter.addUser(user);
                }
            }
        });

    }

    /*SignUpView*/
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void addUser() {
        Toast.makeText(this,"Sign up successfully", Toast.LENGTH_SHORT).show();
        Intent intentHome = new Intent(this, Home.class);
        startActivity(intentHome);
        finish();
    }

    @Override
    public void onShowError(int resMsg) {
        Toast.makeText(this,R.string.error_phone_number_register, Toast.LENGTH_SHORT).show();
    }
}
