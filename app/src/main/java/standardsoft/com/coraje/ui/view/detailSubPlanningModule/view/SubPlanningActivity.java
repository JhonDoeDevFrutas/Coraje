package standardsoft.com.coraje.ui.view.detailSubPlanningModule.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.Planning;
import standardsoft.com.coraje.data.model.entities.Status;
import standardsoft.com.coraje.data.model.entities.SubPlanning;
import standardsoft.com.coraje.ui.view.detailSubPlanningModule.SubPlanningPresenter;
import standardsoft.com.coraje.ui.view.detailSubPlanningModule.SubPlanningPresenterClass;
import standardsoft.com.coraje.ui.view.detailSubPlanningModule.view.adapter.RequestAdapter;
import standardsoft.com.coraje.utilies.CommonUtils;

public class SubPlanningActivity extends AppCompatActivity implements SubPlanningView{

    // Referencias UI
    ProgressBar progressBar;
    private RecyclerView mReciclador;
    private RequestAdapter mAdapter;

    //a list to subPlanning all the section from firebase database
    List<SubPlanning> subPlanningList;

    private String mIdPlanning;

    ArrayList<Developer> mDevelopersList;

    private SubPlanningPresenter mPresenter;

    String mDescriptionDeveloper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_planning);

        Bundle args = new Bundle();
        args = getIntent().getExtras();
        if (args != null){
            mIdPlanning = args.getString(Planning.ID);
        }

        mPresenter = new SubPlanningPresenterClass(this);
        mPresenter.onCreate();

        // Preparar elementos UI
        prepararFab();
        prepararUI();
        prepararToolbar();
        configAdapter(new ArrayList<SubPlanning>());
    }

    private void prepararFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_sub_planning);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAdd();
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
    }

    private void prepararUI() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mReciclador = (RecyclerView)findViewById(R.id.recycler_sub_planning);
    }

    private void prepararToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void configAdapter(List<SubPlanning> subPlanningList) {
        mAdapter = new RequestAdapter(this, subPlanningList);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(new RequestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SubPlanning clickedPlanning) {

            }
        });
        mAdapter.setOnItemLongClickListener(new RequestAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(SubPlanning longClickedSubPlanning) {

                return false;
            }
        });

        mReciclador.setAdapter(mAdapter);
    }

    private void showDialogAdd(){
        mDescriptionDeveloper = null;

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SubPlanningActivity.this);
        View viewAdd = getLayoutInflater().inflate(R.layout.dialog_sub_planning, null);
        alertDialog.setView(viewAdd);

        alertDialog.setTitle("Nuevo");
        alertDialog.setView(viewAdd);
        alertDialog.setIcon(R.drawable.ic_add);

        final EditText edtTask        = (EditText) viewAdd.findViewById(R.id.edt_task);
        final MaterialSpinner developersSpinner = (MaterialSpinner)viewAdd.findViewById(R.id.developers_spinner);

        developersSpinner.setItems(getListDeveloper());
        developersSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mDescriptionDeveloper = item;
            }
        });


        alertDialog.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}});

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        final AlertDialog dialog = alertDialog.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.validatePlanning(getBaseContext(), edtTask)){
                    SubPlanning subPlanning = new SubPlanning();
                    subPlanning.setTask(edtTask.getText().toString());
                    subPlanning.setAssignee(searchDeveloperByName(mDescriptionDeveloper));
                    subPlanning.setStatus(Status.getStatus("ESPERANDO REVISION"));

                    Date date = new Date();
                    long starDate = date.getTime();
                    subPlanning.setDate(starDate);

                    mPresenter.addSubPlanning(mIdPlanning, subPlanning);
                    dialog.dismiss();

                }
            }
        });
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


    // Lista developers
    private List<String> getListDeveloper() {
        List<String> stringList = new ArrayList<>();
        stringList.add("INFORMATICO");
        for (Developer developer : mDevelopersList) {
            stringList.add(developer.getName());
        }

        return stringList;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume(mIdPlanning);
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

    /*SubPlanningView*/
    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void requestDeveloper(List<Developer> developersDatas) {
        mDevelopersList = new ArrayList<>();

        for (Developer developer : developersDatas) {
            mDevelopersList.add(developer);
        }
    }

    @Override
    public void requestSubPlanning(List<SubPlanning> subPlanningsDatas) {
        configAdapter(subPlanningsDatas);
    }

    @Override
    public void addSubPlanning() {

    }

    @Override
    public void updateSubPlanning() {

    }

    @Override
    public void onShowError(int resMsg) {

    }
}
