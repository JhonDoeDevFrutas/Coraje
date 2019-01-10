package standardsoft.com.coraje.ui.view.planningModule.view;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.Customer;
import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.Module;
import standardsoft.com.coraje.data.model.entities.Planning;
import standardsoft.com.coraje.data.model.entities.Priority;
import standardsoft.com.coraje.data.model.entities.Project;
import standardsoft.com.coraje.data.model.entities.Status;
import standardsoft.com.coraje.ui.view.planningModule.PlanningPresenter;
import standardsoft.com.coraje.ui.view.planningModule.PlanningPresenterClass;
import standardsoft.com.coraje.utilies.CommonUtils;

public class PlanningActivity extends AppCompatActivity implements PlanningView {

    // Referencias UI
    MaterialSpinner customersSpinner, modulesSpinner, prioritysSpinner, projectsSpinner, developersSpinner;
    EditText edtTask, edtDescription;
    Button btnSave;
    ProgressBar progressBar;

    ArrayList<Customer> mCustomersList;
    ArrayList<Project> mProjectsList;
    ArrayList<Developer> mDevelopersList;

    private Planning mPlanning;
    private PlanningPresenter mPresenter;

    String mDescriptionModule, mDescriptionPriority, mNameEntidad, mDescriptionProject,mDescriptionDeveloper;
    Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        mPresenter = new PlanningPresenterClass(this);
        mPresenter.onCreate();

        // Preparar elementos UI
        prepararUI();
        configSpinner();

        args = new Bundle();
        args = getIntent().getExtras();

        if (args != null) {
            configPlanning(args);
        }
        prepararToolbar();
    }

    private void prepararUI() {
        projectsSpinner = (MaterialSpinner) findViewById(R.id.projects_spinner);
        customersSpinner = (MaterialSpinner) findViewById(R.id.customers_spinner);
        modulesSpinner = (MaterialSpinner) findViewById(R.id.modules_spinner);
        prioritysSpinner = (MaterialSpinner) findViewById(R.id.prioritys_spinner);
        developersSpinner = (MaterialSpinner) findViewById(R.id.developers_spinner);

        edtTask = (EditText) findViewById(R.id.edt_task);
        edtDescription = (EditText) findViewById(R.id.edt_description);

        btnSave = (Button) findViewById(R.id.btn_save);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDescriptionModule == null || mDescriptionModule.isEmpty()) {
                    Snackbar.make(view, "Debe seleccionar un modulo", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (mDescriptionPriority == null || mDescriptionPriority.isEmpty()) {
                    Snackbar.make(view, "Debe seleccionar una prioridad", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (CommonUtils.validatePlanning(getBaseContext(), edtTask)) {
                    Planning planning = new Planning();
                    planning.setTask(edtTask.getText().toString().trim());
                    planning.setDescription(edtDescription.getText().toString().trim());
                    planning.setStatus(Status.getStatus("ESPERANDO REVISION"));
                    planning.setModule(Module.getModule(mDescriptionModule));           // get module
                    planning.setProject(searchProjectByName(mDescriptionProject));      //get project
                    planning.setAssignee(searchDeveloperByName(mDescriptionDeveloper)); //get developer
                    planning.setCustomer(searchCustomerByName(mNameEntidad));
                    planning.setPriority(Priority.getPriority(mDescriptionPriority));   // get priority

                    if (mPlanning != null){
                        planning.setId(mPlanning.getId());
                        planning.setDate(mPlanning.getDate());
                        mPresenter.updatePlanning(planning);
                    }else {
                        Date date = new Date();
                        long starDate = date.getTime();

                        planning.setDate(starDate);
                        mPresenter.addPlanning(planning);
                    }

                    finish();
                }
            }
        });
    }

    private void configSpinner() {
        List<String> moduleList = new ArrayList<>();
        moduleList.add("SELECCIONE MODULO");
        for (Module module : Module.values()) {
            moduleList.add(module.getDescription());
        }
        modulesSpinner.setItems(moduleList);
        modulesSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Module module = Module.getModule(item);

                if (module != null) {
                    mDescriptionModule = item;
                } else {
                    mDescriptionModule = null;
                    Snackbar.make(view, "Debe seleccionar un modulo", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        List<String> priorityList = new ArrayList<>();
        priorityList.add("SELECCIONE PRIORIDAD");
        for (Priority priority : Priority.values()) {
            priorityList.add(priority.getDescription());
        }
        prioritysSpinner.setItems(priorityList);
        prioritysSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Priority priority = Priority.getPriority(item);

                if (priority != null) {
                    mDescriptionPriority = item;
                } else {
                    mDescriptionPriority = null;
                    Snackbar.make(view, "Debe seleccionar una prioridad", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        onConfigProjectSpinner(new ArrayList<Project>(0));
        onConfigCustomerSpinner(new ArrayList<Customer>(0));
        onConfigDeveloperSpinner(new ArrayList<Developer>(0));

        customersSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mNameEntidad = item;
            }
        });

        projectsSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mDescriptionProject = item;
            }
        });

        developersSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mDescriptionDeveloper = item;
            }
        });
    }

    private void prepararToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mPlanning != null){
            toolbar.setTitle("Ajustes");
        }
        toolbar.setSubtitle("PlanningActivity");
        setSupportActionBar(toolbar);
    }

    public void configPlanning(Bundle args) {
        mPlanning = new Planning();
        mPlanning.setId(args.getString(Planning.ID));
        mPlanning.setDescription(args.getString(Planning.DESCRIPTION));
        mPlanning.setDate(Long.parseLong(args.getString(Planning.DATE)));
        mPlanning.setEstimation(args.getInt(Planning.ESTIMATION));
        mPlanning.setPercentage(args.getInt(Planning.PERCENTAGE));
        mPlanning.setTask(args.getString(Planning.TASK));

        mDescriptionModule = args.getString(Planning.MODULE);
        mDescriptionPriority = args.getString(Planning.PRIORITY);

        mPlanning.setModule(Module.getModule(mDescriptionModule));              // get module
        mPlanning.setPriority(Priority.getPriority(mDescriptionPriority));      // get priority
        mPlanning.setStatus(Status.getStatus(args.getString(Planning.STATUS))); // get status

        edtTask.setText(mPlanning.getTask());
        edtDescription.setText(mPlanning.getDescription());

        List<String> moduleList = modulesSpinner.getItems();
        int getModulePosition = getPosition(moduleList, mPlanning.getModule().getDescription());
        modulesSpinner.setSelectedIndex(getModulePosition);

        List<String> priorityList = prioritysSpinner.getItems();
        int getPriorityPosition = getPosition(priorityList, mPlanning.getPriority().getDescription());
        prioritysSpinner.setSelectedIndex(getPriorityPosition);
    }

    void configPlanningSpinner(){
        mNameEntidad = args.getString(Planning.CUSTOMER);
        mDescriptionProject = args.getString(Planning.PROJECT);
        mDescriptionDeveloper = args.getString(Planning.ASSIGNEE);

        if (mNameEntidad != null & mCustomersList != null){
            mPlanning.setCustomer(searchCustomerByName(mNameEntidad)); //get customer
        }
        if (mDescriptionProject != null & mProjectsList != null){
            mPlanning.setProject(searchProjectByName(mDescriptionProject));// get priority
        }
        if (mDescriptionDeveloper != null & mDevelopersList != null){
            mPlanning.setAssignee(searchDeveloperByName(mDescriptionDeveloper));// get developer
        }

        if (mPlanning.getCustomer() != null){
            List<String> customerList = customersSpinner.getItems();
            int customerPosition = getPosition(customerList, mPlanning.getCustomer().getName());
            customersSpinner.setSelectedIndex(customerPosition);
        }

        if (mPlanning.getProject() != null){
            List<String> projectList = projectsSpinner.getItems();
            int projectPosition = getPosition(projectList, mPlanning.getProject().getDescription());
            projectsSpinner.setSelectedIndex(projectPosition);
        }

        if (mPlanning.getAssignee() != null){
            List<String> developerList = developersSpinner.getItems();
            int developertPosition = getPosition(developerList, mPlanning.getAssignee().getName());
            developersSpinner.setSelectedIndex(developertPosition);
        }
    }

    private int getPosition(List<String> stringList, String description) {
        int index = 0;
        for (String data : stringList) {
            if (data.equals(description)) {
                return index;
            }
            index++;
        }
        return index;
    }

    // Search customer
    private Customer searchCustomerByName(String name) {
        Customer customer = null;
        for (Customer customerTmp : mCustomersList) {
            if (customerTmp.getName().equals(name)) {
                customer = customerTmp;
                break;
            }
        }
        return customer;
    }

    // Search project
    private Project searchProjectByName(String name) {
        Project project = null;
        for (Project projectTmp : mProjectsList) {
            if (projectTmp.getDescription().equals(name)) {
                project = projectTmp;
                break;
            }
        }
        return project;
    }

    // Search developer
    private Developer searchDeveloperByName(String name) {
        Developer developer = null;
        for (Developer developer1 : mDevelopersList) {
            if (developer1.getName().equals(name)) {
                developer = developer1;
                break;
            }
        }
        return developer;
    }

    // Datos customers firebase
    void onConfigCustomerSpinner(List<Customer> customerList) {
        List<String> stringList = getListCustomer(customerList);
        customersSpinner.setItems(stringList);
    }

    // Datos customers firebase
    void onConfigProjectSpinner(List<Project> projectList) {
        List<String> stringList = getListProject(projectList);
        projectsSpinner.setItems(stringList);
    }

    // Lista spinner customer
    private List<String> getListCustomer(List<Customer> customerList) {
        List<String> stringList = new ArrayList<>();
        stringList.add("ENTIDAD");
        for (Customer customer : customerList) {
            stringList.add(customer.getName());
        }

        return stringList;
    }

    // Datos developers firebase
    void onConfigDeveloperSpinner(List<Developer> developerList) {
        List<String> stringList = getListDeveloper(developerList);
        developersSpinner.setItems(stringList);
    }

    // Lista spinner developers
    private List<String> getListDeveloper(List<Developer> developerList) {
        List<String> stringList = new ArrayList<>();
        stringList.add("INFORMATICO");
        for (Developer developer : developerList) {
            stringList.add(developer.getName());
        }

        return stringList;
    }


    // Lista spinner project
    private List<String> getListProject(List<Project> projectList) {
        List<String> stringList = new ArrayList<>();
        stringList.add("PROYECTO");
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
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void resultCustomer(List<Customer> customersDatas) {
        mCustomersList = new ArrayList<>();

        for (Customer customer : customersDatas) {
            mCustomersList.add(customer);
        }

        onConfigCustomerSpinner(customersDatas);
        if (mPlanning != null) {
            configPlanningSpinner();
        }
    }

    @Override
    public void resultProject(List<Project> projectsDatas) {
        mProjectsList = new ArrayList<>();

        for (Project project : projectsDatas) {
            mProjectsList.add(project);
        }

        onConfigProjectSpinner(projectsDatas);
        if (mPlanning != null){
            configPlanningSpinner();
        }
    }

    @Override
    public void resultDeveloper(List<Developer> developersDatas) {
        mDevelopersList = new ArrayList<>();

        for (Developer developer : developersDatas) {
            mDevelopersList.add(developer);
        }

        onConfigDeveloperSpinner(developersDatas);
        if (mPlanning != null){
            configPlanningSpinner();
        }
    }


    @Override
    public void planningAdded() {
    }

    @Override
    public void updateSuccess() {


    }


    @Override
    public void onShowError(int resMsg) {
        Toast.makeText(this, resMsg, Toast.LENGTH_LONG).show();
    }
}
