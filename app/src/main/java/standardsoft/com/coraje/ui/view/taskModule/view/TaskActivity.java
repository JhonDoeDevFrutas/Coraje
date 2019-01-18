package standardsoft.com.coraje.ui.view.taskModule.view;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.Status;
import standardsoft.com.coraje.data.model.entities.SubPlanning;
import standardsoft.com.coraje.ui.view.taskModule.TaskPresenter;
import standardsoft.com.coraje.ui.view.taskModule.TaskPresenterClass;

public class TaskActivity extends AppCompatActivity implements TaskView{

    // Referencias UI
    MaterialSpinner developersSpinner, statusSpinner;
    EditText edtTime, edtNotas;
    CollapsingToolbarLayout toolbarLayout;
    private CoordinatorLayout contentTask;

    private TaskPresenter mPresenter;

    ArrayList<Developer> mDevelopersList;

    String mDescriptionStatus, mDescriptionDeveloper;
    SubPlanning mSubPlanning;
    String mIdPlanning;
    Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        mPresenter = new TaskPresenterClass(this);
        mPresenter.onCreate();

        // Preparar elementos UI
        prepararUI();
        prepararFab();

        args = new Bundle();
        args = getIntent().getExtras();
        if (args != null){
            configSubPlanning(args);
        }

        prepararToolbar();
    }

    private void prepararUI() {
        contentTask = (CoordinatorLayout)findViewById(R.id.contentTask);
        toolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        developersSpinner = (MaterialSpinner)findViewById(R.id.developers_spinner);
        statusSpinner = (MaterialSpinner)findViewById(R.id.status_spinner);
        edtTime = (EditText)findViewById(R.id.edt_time_stimation);
        edtNotas = (EditText)findViewById(R.id.edt_notas);

        configSpinner();
    }

    // set subplanning
    private void configSubPlanning(Bundle args){
        mIdPlanning = args.getString(SubPlanning.ID_PLANNING);
        mDescriptionDeveloper = args.getString(SubPlanning.ASSIGNEE);
        mDescriptionStatus = args.getString(SubPlanning.STATUS);

        mSubPlanning = new SubPlanning();
        mSubPlanning.setId(args.getString(SubPlanning.ID));
        //mSubPlanning.setIdPlanning(args.getString(SubPlanning.ID_PLANNING));
        mSubPlanning.setTaskPlanning(args.getString(SubPlanning.TASK_PLANNING));
        mSubPlanning.setTask(args.getString(SubPlanning.TASK));
        mSubPlanning.setStatus(Status.getStatus(mDescriptionStatus));
        mSubPlanning.setEstimation(args.getInt(SubPlanning.ESTIMATION));    // get status
        mSubPlanning.setDate(Long.parseLong(args.getString(SubPlanning.DATE)));
        mSubPlanning.setDescription(args.getString(SubPlanning.DESCRIPTION));

        if (mSubPlanning.getEstimation() != 0 ){
            edtTime.setText(Long.toString(mSubPlanning.getEstimation()));
        }
        edtNotas.setText(mSubPlanning.getDescription());

        configSubPlanningSpinner();
    }

    // set position spinner
    private void configSubPlanningSpinner(){
        List<String> statusList = statusSpinner.getItems();
        int position = getPosition(statusList, mSubPlanning.getStatus().getDescription());
        statusSpinner.setSelectedIndex(position);

        if (mDescriptionDeveloper != null & mDevelopersList != null){
            mSubPlanning.setAssignee(searchDeveloperByName(mDescriptionDeveloper)); // get developer
        }

        if (mSubPlanning.getAssignee() != null){
            List<String> developerList = developersSpinner.getItems();
            int developertPosition = getPosition(developerList, mSubPlanning.getAssignee().getName());
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

    // Search developer
    private Developer searchDeveloperByName(String name) {
        Developer developer = null;
        for (Developer developerTmp : mDevelopersList) {
            if (developerTmp.getName().equals(name)) {
                developer = developerTmp;
                break;
            }
        }
        return developer;
    }


    private void prepararToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (mSubPlanning != null){
            toolbarLayout.setTitle(mSubPlanning.getTask().toString());
        }
    }


    private void configSpinner() {
        List<String> statusList = new ArrayList<>();
        for (Status status : Status.values()) {
            statusList.add(status.getDescription());
        }

        List<String> developersList = getListDeveloper(new ArrayList<Developer>(0));

        statusSpinner.setItems(statusList);
        developersSpinner.setItems(developersList);

        statusSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mDescriptionStatus = item;
            }
        });

        developersSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mDescriptionDeveloper = item;
            }
        });
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

    private void configTask(){

    }

    private void prepararFab() {
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                saveOrEdit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveOrEdit() {
        String strTime = edtTime.getText().toString();
        int time = strTime.isEmpty() ? 0 : Integer.parseInt(strTime);

        mSubPlanning.setAssignee(searchDeveloperByName(mDescriptionDeveloper));
        mSubPlanning.setStatus(Status.getStatus(mDescriptionStatus));
        mSubPlanning.setEstimation(time);
        mSubPlanning.setDescription(edtNotas.getText().toString());

        mPresenter.update(mIdPlanning, mSubPlanning);
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


    //TaskView
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void resultDeveloper(List<Developer> developersDatas) {
        mDevelopersList = new ArrayList<>();

        for (Developer developer : developersDatas) {
            mDevelopersList.add(developer);
        }

        if (!mDevelopersList.isEmpty()){
            List<String> developersList = getListDeveloper(mDevelopersList);
            developersSpinner.setItems(developersList);

            configSubPlanningSpinner();
        }
    }

    @Override
    public void updateSuccess() {
        Snackbar.make(contentTask, R.string.add_message_request, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        finish();
    }
}
