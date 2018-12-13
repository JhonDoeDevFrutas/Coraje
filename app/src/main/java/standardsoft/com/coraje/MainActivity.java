package standardsoft.com.coraje;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import standardsoft.com.coraje.ui.view.Home;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intentHome = new Intent(this, Home.class);

        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intentHome);
                    finish();
                    return;
                }
            }
        };
        timer.start();

    }
}
