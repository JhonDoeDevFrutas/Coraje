package standardsoft.com.coraje.ui.view.planningModule.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.Customer;
import standardsoft.com.coraje.data.model.entities.Module;
import standardsoft.com.coraje.data.model.entities.Planning;
import standardsoft.com.coraje.data.model.entities.Priority;
import standardsoft.com.coraje.data.model.entities.Status;
import standardsoft.com.coraje.ui.view.planningModule.PlanningPresenter;
import standardsoft.com.coraje.ui.view.planningModule.PlanningPresenterClass;

public class PlanningActivity extends AppCompatActivity implements PlanningView{

    // Referencias UI
    MaterialSpinner customersSpinner, modulesSpinner, prioritysSpinner;
    EditText edtTask, edtDescription;
    Button btnSave;

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

        List<String>moduleList = new ArrayList<>();
        List<String>priorityList = new ArrayList<>();

        for (Module module : Module.values()) {
            moduleList.add(module.getDescription());
        }

        for (Priority priority : Priority.values()) {
            priorityList.add(priority.getDescription());
        }

        modulesSpinner.setItems(moduleList);
        prioritysSpinner.setItems(priorityList);

        edtTask = (EditText)findViewById(R.id.edt_task);
        edtDescription = (EditText)findViewById(R.id.edt_description);

        btnSave = (Button)findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String description = edtDescription.getText().toString();
                String task = edtTask.getText().toString();

                Status status = Status.esperando;
                Priority priority = Priority.baja;
                Customer customer = null;
                Module module = Module.app;

                Planning planning = new Planning(description, status, priority, customer, module, task);
                mPresenter.addPlanning(planning);

            }
        });
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
