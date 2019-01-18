package standardsoft.com.coraje.ui.view.detailActivityModule.view;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.SubPlanning;
import standardsoft.com.coraje.data.model.entities.User;
import standardsoft.com.coraje.data.preferences.SessionPrefs;
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

    //a list to store all the subPlanning from firebase database
    List<SubPlanning> subPlanningList;
    List<SubPlanning> searchSubPlanning;

    // Search Functionality
    TaskAdapter mSearchAdapter;
    List<String> suggesList = new ArrayList<>(); //list filter
    MaterialSearchBar materialSearchBar;


    private ActivitysPresenter mPresenter;
    String mUserName, mUserPhone;
    boolean mSelectAll;

    public ActivitysFragment() {
        mPresenter = new ActivitysPresenterClass(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_activitys, container, false);
        setHasOptionsMenu(true);

        Bundle arguments = getArguments();
        mUserName   = arguments.getString(User.NAME);// Recuperamos información
        mUserPhone  = arguments.getString(User.PHONE);

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

        prepararSearchBar();
    }

    private void prepararSearchBar() {
        // Search
        materialSearchBar = (MaterialSearchBar)view.findViewById(R.id.searchBar);
        materialSearchBar.setHint("Buscar");
        materialSearchBar.setLastSuggestions(suggesList);
        materialSearchBar.setCardViewElevation(10);

        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //when user type their text, we will change suggest list
                List<String>suggest = new ArrayList<String>();
                for (String search : suggesList) {
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //when search bar is close
                //restore original adapter
                if (!enabled)
                    mReciclador.setAdapter(mAdapter);

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                // when search finish
                //show result of search adapter
                String find = text.toString().trim();
                searchSubPlanning = new ArrayList<>();
                for (SubPlanning subPlanning : subPlanningList) {
                    String task = subPlanning.getTask().trim();
                    if (task.equals(find)){
                        searchSubPlanning.add(subPlanning);
                    }
                }

                startSearch();
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }

    // start search component
    private void startSearch() {
        // when search finish
        // show result of search adapter
        // start search component
        // Reset Adapter and recycler
        mSearchAdapter = new TaskAdapter(getActivity(), searchSubPlanning);
        mSearchAdapter.notifyDataSetChanged();

        mSearchAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SubPlanning clickedPlanning) {
                onDetailTask(clickedPlanning, 0);
            }
        });

        mSearchAdapter.setOnItemLongClickListener(new TaskAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(SubPlanning longClickedSubPlanning) {
                onTask(longClickedSubPlanning, 1);
                return false;
            }
        });

        mReciclador.setAdapter(mSearchAdapter);// set adapter for recycler view is search result
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

        mReciclador.setAdapter(mAdapter);   // set adapter for recycler view is search result
    }

    private void onTask(SubPlanning subPlanning, int typeSend){
        Intent intentTask = new Intent(getActivity(), TaskActivity.class);
        intentTask.putExtra(User.NAME, mUserName);
        intentTask.putExtra(SubPlanning.ID, subPlanning.getId());
        intentTask.putExtra(SubPlanning.ID_PLANNING, subPlanning.getIdPlanning());
        intentTask.putExtra(SubPlanning.TASK_PLANNING, subPlanning.getTaskPlanning());
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
        intentDetailTask.putExtra(User.NAME, mUserName);
        intentDetailTask.putExtra(SubPlanning.ID, subPlanning.getId());
        intentDetailTask.putExtra(SubPlanning.ID_PLANNING, subPlanning.getIdPlanning());
        intentDetailTask.putExtra(SubPlanning.TASK_PLANNING, subPlanning.getTaskPlanning());
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
    private void loadSuggest() {
        for (int i = 0; i < subPlanningList.size(); i++){
            SubPlanning subPlanning = subPlanningList.get(i);
            String task = subPlanning.getTask().trim();
            suggesList.add(task); // add name of task to suggest list
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume(mUserPhone, mSelectAll);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_select_all:
                mSelectAll = true;
                mPresenter.onResume(mUserPhone, mSelectAll);
        }

        return super.onOptionsItemSelected(item);
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
        // clear all list
        subPlanningList = new ArrayList<>();
        suggesList      = new ArrayList<>();
        subPlanningList = datas;

        loadSuggest();
        configAdapter(datas);
    }

    @Override
    public void showError(int resMsg) {

    }
}
