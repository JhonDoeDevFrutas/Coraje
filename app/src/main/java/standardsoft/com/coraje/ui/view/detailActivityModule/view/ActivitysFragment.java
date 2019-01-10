package standardsoft.com.coraje.ui.view.detailActivityModule.view;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.SubPlanning;
import standardsoft.com.coraje.data.preferences.FirebaseReferences;
import standardsoft.com.coraje.ui.view.detailActivityModule.ActivitysPresenter;
import standardsoft.com.coraje.ui.view.detailActivityModule.ActivitysPresenterClass;
import standardsoft.com.coraje.ui.view.detailActivityModule.view.adapter.TaskAdapter;
import standardsoft.com.coraje.ui.view.detailTaskModule.view.DetailTaskActivity;
import standardsoft.com.coraje.ui.view.taskModule.view.TaskActivity;
import standardsoft.com.coraje.utilies.Uweb;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivitysFragment extends Fragment implements ActivitysView{

    // Referencias UI
    private View view;
    private RecyclerView mReciclador;
    private TaskAdapter mAdapter;
    ProgressBar progressBar;

    //a list to planning all the section from firebase database
    List<SubPlanning> subPlanningList;

    DatabaseReference mDbReference;//our database reference object

    private ActivitysPresenter mPresenter;

    public ActivitysFragment() {
        mPresenter = new ActivitysPresenterClass(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_activitys, container, false);

/*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Firebase Init
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        //getting the reference of node
        mDbReference = database.getReference(FirebaseReferences.SUBPLANNING_REFERENCE);
*/
        mPresenter.onCreate();

        // Checks if the device has any active internet connection.
        if (!Uweb.isNetworkConnected(getActivity())){
            onShowNetWorkError(getString(R.string.error_network));
        }

        //list to planning
        subPlanningList = new ArrayList<>();
        // Preparar elementos UI
        prepararUI();
        configAdapter(new ArrayList<SubPlanning>());
        prepararToolbar();

        return view;
    }

    private void prepararUI() {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mReciclador = (RecyclerView)view.findViewById(R.id.recycler_task);
        mAdapter    = new TaskAdapter(getActivity(), new ArrayList<SubPlanning>(0));
    }

    private void configAdapter(List<SubPlanning> subPlanningList) {
        mReciclador.setAdapter(mAdapter);

        //adapter
        mAdapter = new TaskAdapter(getActivity(), subPlanningList);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SubPlanning clickedPlanning) {
                onDetailTask(clickedPlanning, 0);
            }
        });

        mAdapter.setOnItemLongClickListener(new TaskAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(SubPlanning longClickedSubPlanning) {
                onTask(longClickedSubPlanning, 1);
                return false;
            }
        });

        mReciclador.setAdapter(mAdapter);
    }

    private void onTask(SubPlanning subPlanning, int typeSend){
        Intent intentTask = new Intent(getActivity(), TaskActivity.class);
        intentTask.putExtra(SubPlanning.ID, subPlanning.getId());
        intentTask.putExtra(SubPlanning.ID_PLANNING, subPlanning.getIdPlanning());
        intentTask.putExtra(SubPlanning.TASK, subPlanning.getTask());
        intentTask.putExtra(SubPlanning.ASSIGNEE, subPlanning.getAssignee() != null ? subPlanning.getAssignee().getName() : null);
        intentTask.putExtra(SubPlanning.ESTIMATION, subPlanning.getEstimation());
        intentTask.putExtra(SubPlanning.STATUS, subPlanning.getStatus().getDescription());
        intentTask.putExtra(SubPlanning.DATE, Long.toString(subPlanning.getDate()));
        intentTask.putExtra(SubPlanning.DESCRIPTION, subPlanning.getDescription());

        startActivity(intentTask);
    }

    private void onDetailTask(SubPlanning subPlanning, int typeSend){
        Intent intentDetailTask = new Intent(getActivity(), DetailTaskActivity.class);
        intentDetailTask.putExtra(SubPlanning.ID, subPlanning.getId());
        intentDetailTask.putExtra(SubPlanning.ID_PLANNING, subPlanning.getIdPlanning());
        intentDetailTask.putExtra(SubPlanning.TASK, subPlanning.getTask());
        intentDetailTask.putExtra(SubPlanning.ASSIGNEE, subPlanning.getAssignee() != null ? subPlanning.getAssignee().getName() : null);
        intentDetailTask.putExtra(SubPlanning.ESTIMATION, subPlanning.getEstimation());
        intentDetailTask.putExtra(SubPlanning.STATUS, subPlanning.getStatus().getDescription());
        intentDetailTask.putExtra(SubPlanning.DATE, Long.toString(subPlanning.getDate()));
        intentDetailTask.putExtra(SubPlanning.DESCRIPTION, subPlanning.getDescription());
        startActivity(intentDetailTask);
    }

    private void prepararToolbar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("ACTIVIDADES");// Toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("ActivitysFragment");
    }

    private void onShowNetWorkError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    /*@Override
    public void onStart() {
        super.onStart();
        //attaching value event listener
        mDbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous subplanning list
                subPlanningList.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot snapshot : postSnapshot.getChildren()) {
                        // getting subplanning
                        SubPlanning subPlanning = snapshot.getValue(SubPlanning.class);
                        //adding subplanning to the list
                        subPlanningList.add(subPlanning);
                    }
                }

                //adapter
                mAdapter = new TaskAdapter(getActivity(), subPlanningList);
                mAdapter.notifyDataSetChanged();
                mAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(SubPlanning clickedPlanning) {

                    }
                });

                mAdapter.setOnItemLongClickListener(new TaskAdapter.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(SubPlanning longClickedSubPlanning) {
                        return false;
                    }
                });

                mReciclador.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
*/
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    /*ActivitysView*/
    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void resultSubPlanning(List<SubPlanning> datas) {
        configAdapter(datas);
    }

    @Override
    public void showError(int resMsg) {

    }
}
