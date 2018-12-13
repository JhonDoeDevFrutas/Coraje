package standardsoft.com.coraje.ui.view.planningModule.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.ui.view.planningModule.PlanningPresenter;
import standardsoft.com.coraje.ui.view.planningModule.PlanningPresenterClass;

public class PlanningActivity extends AppCompatActivity implements PlanningView{

    // Referencias UI
    MaterialSpinner customersSpinner, modulesSpinner, prioritysSpinner;
    EditText edtTask, edtDescription;

    private PlanningPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Preparar elementos UI
        prepararUI();

        mPresenter = new PlanningPresenterClass(this);
        mPresenter.onCreate();
    }

    private void prepararUI() {
        customersSpinner = (MaterialSpinner)findViewById(R.id.customers_spinner);
        modulesSpinner = (MaterialSpinner)findViewById(R.id.modules_spinner);
        prioritysSpinner = (MaterialSpinner)findViewById(R.id.prioritys_spinner);

        edtTask = (EditText)findViewById(R.id.edt_task);
        edtDescription = (EditText)findViewById(R.id.edt_description);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    //PlanningView
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void planningAdded() {

    }

    @Override
    public void onShowError(int resMsg) {
        Toast.makeText(this, resMsg, Toast.LENGTH_LONG).show();
    }
}
