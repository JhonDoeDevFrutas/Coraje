package standardsoft.com.coraje.ui.view.detailTaskFragmentModule.view;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
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

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.PromptTask;
import standardsoft.com.coraje.data.model.entities.User;
import standardsoft.com.coraje.ui.view.detailTaskFragmentModule.DetailPromptTaskPresenter;
import standardsoft.com.coraje.ui.view.detailTaskFragmentModule.DetailPromptTaskPresenterClass;
import standardsoft.com.coraje.ui.view.detailTaskFragmentModule.view.adapter.RequestAdapter;
import standardsoft.com.coraje.ui.view.promptTaskModule.view.PromptTaskActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailTaskFragment extends Fragment implements DetailPromptTaskView{

    // Referencias UI
    private View view;
    private RecyclerView mReciclador;
    private RequestAdapter mAdapter;
    ProgressBar progressBar;

    // Search Functionality
    RequestAdapter mSearchAdapter;
    List<String> suggesList = new ArrayList<>(); //list filter
    MaterialSearchBar materialSearchBar;

    private MenuItem itemToHide;

    private DetailPromptTaskPresenter mPresenter;
    String mUserName, mUserPhone;
    String mDescriptionDeveloper;

    //a list to planning all the section from firebase database
    List<PromptTask> mPromptList;
    List<PromptTask> searchPrompt;
    ArrayList<Developer> mDevelopersList;


    public DetailTaskFragment() {
        mPresenter = new DetailPromptTaskPresenterClass(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_detail_task, container, false);

        setHasOptionsMenu(true);

        Bundle arguments = getArguments();
        mUserName   = arguments.getString(User.NAME);// Recuperamos información
        mUserPhone  = arguments.getString(User.PHONE);

        mPresenter.onCreate();

        //list to prompt
        mPromptList = new ArrayList<>();

        // Preparar elementos UI
        prepararUI();
        configAdapter(new ArrayList<PromptTask>(0));
        prepararFab();
        prepararToolbar();

        return view;
    }

    private void prepararUI() {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mReciclador = (RecyclerView)view.findViewById(R.id.recycler_prompt);

        prepararSearchBar();
    }

    private void prepararFab() {
        FloatingActionButton fabPlanning = (FloatingActionButton)view.findViewById(R.id.fab_prompt);
        fabPlanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBugs = new Intent(getActivity(), PromptTaskActivity.class);
                startActivity(intentBugs);
            }
        });
    }

    private void prepararToolbar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("TAREA PUNTUAL");// Toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("DetailTaskFragment");
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
                searchPrompt = new ArrayList<>();
                for (PromptTask promptTask : mPromptList) {
                    String task = promptTask.getTask().trim();
                    if (task.equals(find)){
                        searchPrompt.add(promptTask);
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
        mSearchAdapter = new RequestAdapter(getActivity(), searchPrompt);
        mSearchAdapter.notifyDataSetChanged();

        mSearchAdapter.setOnItemClickListener(new RequestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PromptTask clickedPromptTask) {
                onPromptTask(clickedPromptTask);
            }
        });

        mReciclador.setAdapter(mSearchAdapter);// set adapter for recycler view is search result
    }

    private void onPromptTask(PromptTask promptTask){
        Intent intentPromptTask = new Intent(getActivity(), PromptTaskActivity.class);

        intentPromptTask.putExtra(PromptTask.ID, promptTask.getId());
        intentPromptTask.putExtra(PromptTask.DESCRIPTION, promptTask.getDescription());
        intentPromptTask.putExtra(PromptTask.DATE, Long.toString(promptTask.getDate()));
        intentPromptTask.putExtra(PromptTask.ESTIMATION, promptTask.getEstimation());
        intentPromptTask.putExtra(PromptTask.TASK, promptTask.getTask());

        intentPromptTask.putExtra(PromptTask.CUSTOMER, promptTask.getCustomer() != null ? promptTask.getCustomer().getName() : null);
        intentPromptTask.putExtra(PromptTask.MODULE, promptTask.getModule().getDescription());
        intentPromptTask.putExtra(PromptTask.PRIORITY, promptTask.getPriority().getDescription());
        intentPromptTask.putExtra(PromptTask.ASSIGNEE, promptTask.getAssignee() != null ? promptTask.getAssignee().getName() : null);
        intentPromptTask.putExtra(PromptTask.PROJECT, promptTask.getProject() != null ? promptTask.getProject().getDescription() : null);
        intentPromptTask.putExtra(PromptTask.STATUS, promptTask.getStatus().getDescription());

        startActivity(intentPromptTask);
    }

    private void loadSuggest() {
        for (int i = 0; i < mPromptList.size(); i++){
            PromptTask promptTask = mPromptList.get(i);
            String task = promptTask.getTask().trim();
            suggesList.add(task); // add name of task to suggest list
        }
    }

    private void configAdapter(List<PromptTask> promptTaskList) {
        mAdapter = new RequestAdapter(getActivity(), promptTaskList);
        mAdapter.notifyDataSetChanged();

        mAdapter.setOnItemClickListener(new RequestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PromptTask clickedPromptTask) {
                onPromptTask(clickedPromptTask);
            }
        });

        mReciclador.setAdapter(mAdapter);
    }

    private void showDialogFilters() {
        mDescriptionDeveloper = null;

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        View viewAdd = getActivity().getLayoutInflater().inflate(R.layout.filter_activitys, null);
        alertDialog.setView(viewAdd);

        alertDialog.setTitle("Filtrar x Informatico");
        alertDialog.setView(viewAdd);
        alertDialog.setIcon(R.drawable.ic_add);

        final MaterialSpinner developersSpinner = (MaterialSpinner)viewAdd.findViewById(R.id.developers_spinner);

        developersSpinner.setItems(getListDeveloper());
        developersSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                mDescriptionDeveloper = item;
            }
        });

        alertDialog.setPositiveButton("Aplicar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Developer developer = searchDeveloperByName(mDescriptionDeveloper);
                mUserPhone = developer.getMovil();

                mPresenter.onResume(mUserPhone, false);
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
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


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume(mUserPhone, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options, menu);

        itemToHide = menu.findItem(R.id.item_select_all);
        itemToHide.setVisible(true);   // Mostrar Opción

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_select_all:
                showDialogFilters();
        }

        return super.onOptionsItemSelected(item);
    }

    /*DetailPromptTaskView*/
    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void requestDatas(List<PromptTask> datas) {
        mPromptList = new ArrayList<>();
        suggesList  = new ArrayList<>();
        mPromptList = datas;

        loadSuggest();
        configAdapter(datas);
    }

    @Override
    public void requestDeveloper(List<Developer> developersDatas) {
        mDevelopersList = new ArrayList<>();

        for (Developer developer : developersDatas) {
            mDevelopersList.add(developer);
        }

    }

    @Override
    public void showError(int resMsg) {

    }
}
