package standardsoft.com.coraje.ui.view.planningModule.view;

import android.os.Bundle;
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
import standardsoft.com.coraje.data.model.entities.Project;
import standardsoft.com.coraje.data.model.entities.Status;
import standardsoft.com.coraje.ui.view.planningModule.PlanningPresenter;
import standardsoft.com.coraje.ui.view.planningModule.PlanningPresenterClass;

public class PlanningActivity extends AppCompatActivity implements PlanningView{

    // Referencias UI
    MaterialSpinner customersSpinner, modulesSpinner, prioritysSpinner, projectsSpinner;
    EditText edtTask, edtDescription;
    Button btnSave;

    ArrayList<Customer> mCustomersList;
    ArrayList<Project> mProjectsList;

    private PlanningPresenter mPresenter;

    String mDescriptionModule, mDescriptionPriority, mNameEntidad, mDescriptionProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPresenter = new PlanningPresenterClass(this);
        mPresenter.onCreate();

        // Preparar elementos UI
        prepararUI();
        configUI();
    }

    private void prepararUI() {
        projectsSpinner = (MaterialSpinner)findViewById(R.id.projects_spinner);
        customersSpinner = (MaterialSpinner)findViewById(R.id.customers_spinner);
        modulesSpinner = (MaterialSpinner)findViewById(R.id.modules_spinner);
        prioritysSpinner = (MaterialSpinner)findViewById(R.id.prioritys_spinner);

        edtTask = (EditText)findViewById(R.id.edt_task);
        edtDescription = (EditText)findViewById(R.id.edt_description);

        btnSave = (Button)findViewById(R.id.btn_save);
    }

    private void configUI(){
        List<String>moduleList = new ArrayList<>();
        List<String>priorityList = new ArrayList<>();

        moduleList.add("SELECCIONE MODULO");
        priorityList.add("SELECCIONE PRIORIDAD");

        for (Module module : Module.values()) {
            moduleList.add(module.getDescription());
        }

        for (Priority priority : Priority.values()) {
            priorityList.add(priority.getDescription());
        }

        onConfigProjectSpinner(new ArrayList<Project>(0));
        onConfigCustomerSpinner(new ArrayList<Customer>(0));
        modulesSpinner.setItems(moduleList);
        prioritysSpinner.setItems(priorityList);

        modulesSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mDescriptionModule = item;
            }
        });

        prioritysSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mDescriptionPriority = item;
            }
        });

        customersSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mNameEntidad = item;
            }
        });

        projectsSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mDescriptionProject = item;
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String description = edtDescription.getText().toString();
                String task = edtTask.getText().toString();

                Project project = searchProjectByName(mDescriptionProject);;
                Customer customer = searchCustomerByName(mNameEntidad);
                Status status = Status.getStatus("ESPERANDO REVISION");         // get status
                Module module = Module.getModule(mDescriptionModule);           // get module
                Priority priority = Priority.getPriority(mDescriptionPriority); // get priority

                Planning planning = new Planning(description, status, priority, customer, module, task, project);
                mPresenter.addPlanning(planning);
            }
        });
    }

    // Search customer
    private Customer searchCustomerByName(String name){
        Customer customer = null;
        for (Customer customerTmp : mCustomersList) {
            if (customerTmp.getName().equals(name)){
                customer = customerTmp;
                break;
            }
        }
        return customer;
    }

    // Search project
    private Project searchProjectByName(String name){
        Project project = null;
        for (Project projectTmp : mProjectsList) {
            if (projectTmp.getDescription().equals(name)){
                project = projectTmp;
                break;
            }
        }
        return project;
    }


    // Datos customers firebase
    void onConfigCustomerSpinner(List<Customer> customerList){
        mCustomersList = new ArrayList<>();

        for (Customer customer : customerList) {
            mCustomersList.add(customer);
        }

        List<String>stringList = getListCustomer(mCustomersList);
        customersSpinner.setItems(stringList);
    }

    // Datos customers firebase
    void onConfigProjectSpinner(List<Project> projectList){
        mProjectsList = new ArrayList<>();

        for (Project project : projectList) {
            mProjectsList.add(project);
        }

        List<String>stringList = getListProject(mProjectsList);
        projectsSpinner.setItems(stringList);
    }

    // Lista spinner customer
    private List<String> getListCustomer(List<Customer> customerList) {
        List<String> stringList = new ArrayList<>();
        stringList.add("Seleccione Entidad");
        for (Customer customer : customerList) {
            stringList.add(customer.getName());
        }

        return stringList;
    }

    // Lista spinner project
    private List<String> getListProject(List<Project> projectList) {
        List<String> stringList = new ArrayList<>();
        stringList.add("Seleccione Proyecto");
        for (Project project : projectList) {
            stringList.add(project.getDescription());
        }

        return stringList;
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
    public void resultCustomer(List<Customer> customersDatas) {
        onConfigCustomerSpinner(customersDatas);
    }

    @Override
    public void resultProject(List<Project> projects) {
        onConfigProjectSpinner(projects);
    }

    @Override
    public void planningAdded() {}

    @Override
    public void onShowError(int resMsg) {
        Toast.makeText(this, resMsg, Toast.LENGTH_LONG).show();
    }
}
