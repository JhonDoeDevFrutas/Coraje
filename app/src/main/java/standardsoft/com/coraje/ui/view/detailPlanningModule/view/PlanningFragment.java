package standardsoft.com.coraje.ui.view.detailPlanningModule.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.Planning;
import standardsoft.com.coraje.ui.view.detailPlanningModule.view.adapter.PlanningAdapter;
import standardsoft.com.coraje.ui.view.detailPlanningModule.DetailPlanningPresenter;
import standardsoft.com.coraje.ui.view.detailPlanningModule.DetailPlanningPresenterClass;
import standardsoft.com.coraje.ui.view.detailSubPlanningModule.view.SubPlanningActivity;
import standardsoft.com.coraje.ui.view.planningModule.view.PlanningActivity;
import standardsoft.com.coraje.utilies.Uweb;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanningFragment extends Fragment implements DetailPlanningView{

    // Referencias UI
    private View view;
    private RecyclerView mReciclador;
    private PlanningAdapter mAdapter;

    //a list to planning all the section from firebase database
    List<Planning> planningList;

    DatabaseReference mDbReference;//our database reference object

    private DetailPlanningPresenter mPresenter;

    public PlanningFragment() {
        // Required empty public constructor
        mPresenter = new DetailPlanningPresenterClass(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_planning, container, false);

/*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Firebase Init
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        //getting the reference of node
        mDbReference = database.getReference(FirebaseReferences.PLANNING_REFERENCE);
*/
        mPresenter.onCreate();

        // Checks if the device has any active internet connection.
        if (!Uweb.isNetworkConnected(getActivity())){
            onShowNetWorkError(getString(R.string.error_network));
        }

        //list to planning
        planningList = new ArrayList<>();

        // Preparar elementos UI
        prepararUI();
        configAdapter(new ArrayList<Planning>());
        prepararToolbar();
        prepararFab();
        return view;
    }

    private void prepararToolbar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("TAREAS");           // Toolbar
    }

    private void prepararUI() {
        mAdapter    = new PlanningAdapter(getActivity(), new ArrayList<Planning>(0));
        mReciclador = (RecyclerView)view.findViewById(R.id.recycler_planning);
    }

    private void configAdapter(List<Planning> planningList) {
        mAdapter = new PlanningAdapter(getActivity(), planningList);
        mAdapter.notifyDataSetChanged();

        mAdapter.setOnItemClickListener(new PlanningAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Planning clickedPlanning) {
                onPlanning(clickedPlanning, 0);
            }
        });
        mAdapter.setOnItemLongClickListener(new PlanningAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(Planning longClickedPlanning) {
                onPlanning(longClickedPlanning,1);
                return false;
            }
        });

        mReciclador.setAdapter(mAdapter);

    }


    private void onShowNetWorkError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    private void prepararFab() {
        FloatingActionButton fabPlanning = (FloatingActionButton)view.findViewById(R.id.fab_planning);
        fabPlanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddPlanning();
            }
        });
    }

    private void onAddPlanning() {
        Intent intentPlanning = new Intent(getActivity(), PlanningActivity.class);
        startActivity(intentPlanning);
    }

/*    @Override
    public void onStart() {
        super.onStart();
        //attaching value event listener
        mDbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous planning list
                planningList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // getting planning
                    Planning planning = postSnapshot.getValue(Planning.class);
                    planning.setId(postSnapshot.getKey());
                    //adding planning to the list
                    planningList.add(planning);
                }

                //adapter
                mAdapter = new PlanningAdapter(getActivity(), planningList);
                mAdapter.notifyDataSetChanged();
                mAdapter.setOnItemClickListener(new PlanningAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Planning clickedPlanning) {
                        onPlanning(clickedPlanning, 0);*//*send subPlanning*//*
                    }
                });
                mAdapter.setOnItemLongClickListener(new PlanningAdapter.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(Planning longClickedPlanning) {
                        onPlanning(longClickedPlanning,1);*//*alter planning*//*
                        return false;
                    }
                });
                mReciclador.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/

    private void onPlanning(Planning planning, int typeSend){
        Intent intentPlanning ;
        if (typeSend == 0){
            intentPlanning = new Intent(getActivity(), SubPlanningActivity.class);
        }else {
            intentPlanning = new Intent(getActivity(), PlanningActivity.class);
        }

        intentPlanning.putExtra(Planning.ID, planning.getId());
        intentPlanning.putExtra(Planning.DESCRIPTION, planning.getDescription());
        intentPlanning.putExtra(Planning.DATE, Long.toString(planning.getDate()));
        intentPlanning.putExtra(Planning.ESTIMATION, planning.getEstimation());
        intentPlanning.putExtra(Planning.PERCENTAGE, planning.getPercentage());
        intentPlanning.putExtra(Planning.TASK, planning.getTask());

        intentPlanning.putExtra(Planning.CUSTOMER, planning.getCustomer() != null ? planning.getCustomer().getName() : null);
        intentPlanning.putExtra(Planning.MODULE, planning.getModule().getDescription());
        intentPlanning.putExtra(Planning.PRIORITY, planning.getPriority().getDescription());
        intentPlanning.putExtra(Planning.ASSIGNEE, planning.getAssignee() != null ? planning.getAssignee().getName() : null);
        intentPlanning.putExtra(Planning.PROJECT, planning.getProject() != null ? planning.getProject().getDescription() : null);
        intentPlanning.putExtra(Planning.STATUS, planning.getStatus().getDescription());

        if (typeSend == 0){
            onSubPlanning(intentPlanning);
        }else {
            onPlanning(intentPlanning);
        }
    }

    /*send subPlanning*/
    private void onSubPlanning(Intent intentPlanning) {
        startActivity(intentPlanning);
    }

    /*alter planning*/
    private void onPlanning(Intent intentPlanning) {
        startActivity(intentPlanning);
    }

    @Override
    public void addDatas(List<Planning> datas) {
        configAdapter(datas);
    }

    @Override
    public void showError(int resMsg) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }
}
