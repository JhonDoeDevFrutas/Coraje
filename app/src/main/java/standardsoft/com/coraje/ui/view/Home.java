package standardsoft.com.coraje.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import standardsoft.com.coraje.MainActivity;
import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.User;
import standardsoft.com.coraje.data.preferences.SessionPrefs;
import standardsoft.com.coraje.ui.view.barGraphModule.view.BarGraphFragment;
import standardsoft.com.coraje.ui.view.detailActivityModule.view.ActivitysFragment;
import standardsoft.com.coraje.ui.view.detailBugsFragmentModule.view.DetailBugsFragment;
import standardsoft.com.coraje.ui.view.detailPlanningModule.view.PlanningFragment;
import standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.view.DetailRemarkFragment;
import standardsoft.com.coraje.ui.view.detailTaskFragmentModule.view.DetailTaskFragment;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String mName, mPhone;
    // UI references.
    TextView txtName, txtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navView = navigationView.getHeaderView(0);
        prepararUI(navView);// Preparar elementos UI
        onBringData(getIntent()); // Traer Datos
    }

    private void prepararUI(View view) {
        txtName = (TextView)view.findViewById(R.id.txt_name);
        txtPhone = (TextView)view.findViewById(R.id.txt_phone);
    }

    private void onBringData(Intent intentHome) {
        if (SessionPrefs.get(Home.this).isLoggedIn()){
            // Obtener usuario
            mName   = SessionPrefs.get(getBaseContext()).getName();
            mPhone  = SessionPrefs.get(getBaseContext()).getPhone();
        }else {
            mName   = intentHome.getStringExtra(User.NAME);
            mPhone  = intentHome.getStringExtra(User.PHONE);
        }

        txtName.setText(mName);
        txtPhone.setText(mPhone);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.action_signOff){ // Cerrar Sesión
            SessionPrefs.get(getBaseContext()).logOut();

            onClose();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager manager = getFragmentManager();

        Bundle arguments = new Bundle();
        arguments.putString(User.NAME, mName);
        arguments.putString(User.PHONE, mPhone);

        if (id == R.id.nav_activitys) {
            ActivitysFragment activitysFragment = new ActivitysFragment();
            activitysFragment.setArguments(arguments);

            manager.beginTransaction().replace(R.id.content_frame, activitysFragment).commit();
        } else if (id == R.id.nav_activity_register) {
            manager.beginTransaction().replace(R.id.content_frame, new DetailRemarkFragment()).commit();
        } else if (id == R.id.nav_task) {
            DetailTaskFragment detailTaskFragment = new DetailTaskFragment();
            detailTaskFragment.setArguments(arguments);

            manager.beginTransaction().replace(R.id.content_frame, detailTaskFragment).commit();
        } else if (id == R.id.nav_bugs) {
            DetailBugsFragment detailBugsFragment = new DetailBugsFragment();
            detailBugsFragment.setArguments(arguments);

            manager.beginTransaction().replace(R.id.content_frame, detailBugsFragment).commit();
        } else if (id == R.id.nav_planning) {
            manager.beginTransaction().replace(R.id.content_frame, new PlanningFragment()).commit();
        } else if (id == R.id.nav_developer) {
            manager.beginTransaction().replace(R.id.content_frame, new DetailDeveloperFragment()).commit();
        } else if (id == R.id.nav_customer) {
            manager.beginTransaction().replace(R.id.content_frame, new DetailCustomerFragment()).commit();
        }else if (id == R.id.nav_project) {
            manager.beginTransaction().replace(R.id.content_frame, new DetailProjectsFragment()).commit();
        }else if (id == R.id.nav_graph) {
            manager.beginTransaction().replace(R.id.content_frame, new BarGraphFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void onClose() {
        SessionPrefs.get(getBaseContext()).logOut();

        Intent intentMain = new Intent(this, MainActivity.class);
        startActivity(intentMain);
        finish();
    }
}
