package standardsoft.com.coraje;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;

import standardsoft.com.coraje.data.preferences.SessionPrefs;
import standardsoft.com.coraje.ui.view.Home;
import standardsoft.com.coraje.ui.view.SignInModule.view.SignInActivity;
import standardsoft.com.coraje.ui.view.SignUp.view.SignUpActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView imgWelcome;
    private TextView txtSlogan;
    private Button btnSignUp, btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Preparar elementos UI
        prepararUI();

        //splash screen
        KenBurnsView kbv = (KenBurnsView) findViewById(R.id.background);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                startPage();
            }
        },3000);
    }

    private void prepararUI() {
        imgWelcome = (ImageView) findViewById(R.id.image_welcome);
        Animation myAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.mytransition);
        imgWelcome.startAnimation(myAnim);

        txtSlogan = (TextView) findViewById(R.id.txt_slogan);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/NABILA.TTF");
        txtSlogan.setTypeface(face);

        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        btnSignIn = (Button) findViewById(R.id.btn_sign_in);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignUp = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intentSignUp);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignIn = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intentSignIn);
            }
        });
    }

    private void startPage() {
        // Redirección al Login
        if (SessionPrefs.get(MainActivity.this).isLoggedIn()) {
            Intent intentHome = new Intent(this, Home.class);
            startActivity(intentHome);// Redirección al Home
            finish();
            return;
        }
    }
}
