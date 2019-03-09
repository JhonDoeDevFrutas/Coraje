package standardsoft.com.coraje.ui.view.promptTaskModule.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.Customer;
import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.Module;
import standardsoft.com.coraje.data.model.entities.Priority;
import standardsoft.com.coraje.data.model.entities.Project;
import standardsoft.com.coraje.data.model.entities.PromptTask;
import standardsoft.com.coraje.data.model.entities.Status;
import standardsoft.com.coraje.ui.view.promptTaskModule.PromptTaskPresenter;
import standardsoft.com.coraje.ui.view.promptTaskModule.PromptTaskPresenterClass;
import standardsoft.com.coraje.utilies.CommonUtils;

public class PromptTaskActivity extends AppCompatActivity implements PromptView{

    // Referencias UI
    MaterialSpinner customersSpinner, modulesSpinner, prioritysSpinner, projectsSpinner, developersSpinner, statusSpinner;
    EditText edtTask, edtDescription, edtTime;
    Button btnSave;
    ProgressBar progressBar;

    ArrayList<Customer> mCustomersList;
    ArrayList<Project> mProjectsList;
    ArrayList<Developer> mDevelopersList;

    private PromptTask mPromptTask;
    private PromptTaskPresenter mPresenter;

    String mDescriptionModule, mDescriptionPriority, mNameEntidad, mDescriptionProject, mDescriptionDeveloper, mDescriptionStatus;
    Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt_task);


        mPresenter = new PromptTaskPresenterClass(this);
        mPresenter.onCreate();

        // Preparar elementos UI
        prepararUI();
        configSpinner();

        args = new Bundle();
        args = getIntent().getExtras();
        if (args != null) {
            configBugs(args);
        }

        prepararToolbar();
    }

    private void prepararUI() {
        projectsSpinner = (MaterialSpinner) findViewById(R.id.projects_spinner);
        customersSpinner = (MaterialSpinner) findViewById(R.id.customers_spinner);
        modulesSpinner = (MaterialSpinner) findViewById(R.id.modules_spinner);
        prioritysSpinner = (MaterialSpinner) findViewById(R.id.prioritys_spinner);
        developersSpinner = (MaterialSpinner) findViewById(R.id.developers_spinner);
        statusSpinner = (MaterialSpinner) findViewById(R.id.status_spinner);

        edtTask         = (EditText) findViewById(R.id.edt_task);
        edtDescription  = (EditText) findViewById(R.id.edt_description);
        edtTime         = (EditText)findViewById(R.id.edt_time_stimation);

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
                    String task         = edtTask.getText().toString().trim();
                    String description  = edtDescription.getText().toString().trim();
                    Module module       = Module.getModule(mDescriptionModule);           // get module
                    Project project     = searchProjectByName(mDescriptionProject);      //get project
                    Developer developer = searchDeveloperByName(mDescriptionDeveloper); //get developer
                    Customer customer   = searchCustomerByName(mNameEntidad);
                    Priority priority   = Priority.getPriority(mDescriptionPriority);   // get priority
                    Status status       = mDescriptionStatus != null ? Status.getStatus(mDescriptionStatus) : Status.getStatus("ESPERANDO REVISION");

                    String strTime      = edtTime.getText().toString();
                    int time = strTime.isEmpty() ? 0 : Integer.parseInt(strTime);

                    if (mPromptTask != null){
                        PromptTask promptTask = new PromptTask(description,developer,status,priority,time,customer,module,task,project,mPromptTask.getDate(),mPromptTask.getId());
                        mPresenter.updatePromptTask(promptTask);
                    }else {

                        Date date = new Date();
                        long starDate = date.getTime();

                        PromptTask promptTask = new PromptTask(description, developer,status,priority,time,customer,module,task,project,starDate);
                        mPresenter.addPromptTask(promptTask);
                    }

                    finish();
                }
            }
        });
    }

    private void prepararToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setSubtitle("PromptTaskActivity");
        setSupportActionBar(toolbar);
    }

    private void configBugs(Bundle args) {
        mDescriptionStatus = args.getString(PromptTask.STATUS);

        mPromptTask = new PromptTask();
        mPromptTask.setId(args.getString(PromptTask.ID));
        mPromptTask.setDescription(args.getString(PromptTask.DESCRIPTION));
        mPromptTask.setDate(Long.parseLong(args.getString(PromptTask.DATE)));
        mPromptTask.setEstimation(args.getInt(PromptTask.ESTIMATION));
        mPromptTask.setTask(args.getString(PromptTask.TASK));

        mDescriptionModule = args.getString(PromptTask.MODULE);
        mDescriptionPriority = args.getString(PromptTask.PRIORITY);

        mPromptTask.setModule(Module.getModule(mDescriptionModule));          // get module
        mPromptTask.setPriority(Priority.getPriority(mDescriptionPriority));  // get priority
        mPromptTask.setStatus(Status.getStatus(mDescriptionStatus));          // get status

        edtTask.setText(mPromptTask.getTask());
        edtDescription.setText(mPromptTask.getDescription());
        if (mPromptTask.getEstimation() != 0 ){
            edtTime.setText(Long.toString(mPromptTask.getEstimation()));
        }

        List<String> moduleList = modulesSpinner.getItems();
        int getModulePosition = getPosition(moduleList, mPromptTask.getModule().getDescription());
        modulesSpinner.setSelectedIndex(getModulePosition);

        List<String> priorityList = prioritysSpinner.getItems();
        int getPriorityPosition = getPosition(priorityList, mPromptTask.getPriority().getDescription());
        prioritysSpinner.setSelectedIndex(getPriorityPosition);
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



    private void configSpinner() {
        List<String> moduleList = new ArrayList<>();
        moduleList.add("SELECCIONE MODULO");
        for (Module module : Module.values()) {
            moduleList.add(module.getDescription());
        }
        //Collections.sort(moduleList);//order by description

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
        //Collections.sort(priorityList);//order by description

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

        List<String> statusList = new ArrayList<>();
        for (Status status : Status.values()) {
            statusList.add(status.getDescription());
        }
        statusSpinner.setItems(statusList);
        statusSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mDescriptionStatus = item;
            }
        });

        onConfigProjectSpinner(new ArrayList<Project>(0));
        onConfigCustomerSpinner(new ArrayList<Customer>(0));
        onConfigDeveloperSpinner(new ArrayList<Developer>(0));

        projectsSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mDescriptionProject = item;
            }
        });

        customersSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mNameEntidad = item;
            }
        });


        developersSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mDescriptionDeveloper = item;
            }
        });
    }

    // Datos customers firebase
    void onConfigProjectSpinner(List<Project> projectList) {
        List<String> stringList = getListProject(projectList);
        projectsSpinner.setItems(stringList);
    }

    // Datos customers firebase
    void onConfigCustomerSpinner(List<Customer> customerList) {
        List<String> stringList = getListCustomer(customerList);
        customersSpinner.setItems(stringList);
    }

    // Datos developers firebase
    void onConfigDeveloperSpinner(List<Developer> developerList) {
        List<String> stringList = getListDeveloper(developerList);
        developersSpinner.setItems(stringList);
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

    // Lista spinner customer
    private List<String> getListCustomer(List<Customer> customerList) {
        List<String> stringList = new ArrayList<>();
        stringList.add("ENTIDAD");
        for (Customer customer : customerList) {
            stringList.add(customer.getName());
        }
        return stringList;
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

    void configPromptTaskSpinner() {
        mNameEntidad            = args.getString(PromptTask.CUSTOMER);
        mDescriptionProject     = args.getString(PromptTask.PROJECT);
        mDescriptionDeveloper   = args.getString(PromptTask.ASSIGNEE);

        List<String> statusList = statusSpinner.getItems();
        int position            = getPosition(statusList, mPromptTask.getStatus().getDescription());
        statusSpinner.setSelectedIndex(position);

        if (mNameEntidad != null & mCustomersList != null) {
            mPromptTask.setCustomer(searchCustomerByName(mNameEntidad)); //get customer
        }
        if (mDescriptionProject != null & mProjectsList != null) {
            mPromptTask.setProject(searchProjectByName(mDescriptionProject));// get priority
        }
        if (mDescriptionDeveloper != null & mDevelopersList != null) {
            mPromptTask.setAssignee(searchDeveloperByName(mDescriptionDeveloper));// get developer
        }

        if (mPromptTask.getCustomer() != null) {
            List<String> customerList = customersSpinner.getItems();
            int customerPosition = getPosition(customerList, mPromptTask.getCustomer().getName());
            customersSpinner.setSelectedIndex(customerPosition);
        }

        if (mPromptTask.getProject() != null) {
            List<String> projectList = projectsSpinner.getItems();
            int projectPosition = getPosition(projectList, mPromptTask.getProject().getDescription());
            projectsSpinner.setSelectedIndex(projectPosition);
        }

        if (mPromptTask.getAssignee() != null) {
            List<String> developerList = developersSpinner.getItems();
            int developertPosition = getPosition(developerList, mPromptTask.getAssignee().getName());
            developersSpinner.setSelectedIndex(developertPosition);
        }
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

    /*PromptView*/
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
        if (mPromptTask != null) {
            configPromptTaskSpinner();
        }

    }

    @Override
    public void resultProject(List<Project> projectsDatas) {
        mProjectsList = new ArrayList<>();

        for (Project project : projectsDatas) {
            mProjectsList.add(project);
        }

        onConfigProjectSpinner(projectsDatas);
        if (mPromptTask != null){
            configPromptTaskSpinner();
        }

    }

    @Override
    public void resultDeveloper(List<Developer> developersDatas) {
        mDevelopersList = new ArrayList<>();

        for (Developer developer : developersDatas) {
            mDevelopersList.add(developer);
        }

        onConfigDeveloperSpinner(developersDatas);
        if (mPromptTask != null){
            configPromptTaskSpinner();
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

    }
}
