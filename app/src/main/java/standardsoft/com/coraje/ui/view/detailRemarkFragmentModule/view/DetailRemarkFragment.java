package standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.view;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.Bugs;
import standardsoft.com.coraje.data.model.entities.PromptTask;
import standardsoft.com.coraje.data.model.entities.Remark;
import standardsoft.com.coraje.data.model.entities.SubPlanning;
import standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.DetailRemarkPresenter;
import standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.DetailRemarkPresenterClass;
import standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.view.adapter.RequestAdapter;
import standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.view.adapter.RequestAdapterNew;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailRemarkFragment extends Fragment implements RemarkView{

    // Referencias UI
    private View view;
    ProgressBar progressBar;
    private ListView mListView;
    private RequestAdapterNew mAdapter;

    private DetailRemarkPresenter mPresenter;

    //a list to store all the remark from firebase database
    List<Remark> remarkList;
    List<Bugs> bugsList;
    List<PromptTask> promptList;
    HashMap<String, SubPlanning> subPlanningHashMap;

    public DetailRemarkFragment() {
        // Required empty public constructor
        mPresenter = new DetailRemarkPresenterClass(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail_remark, container, false);

        mPresenter.onCreate();

        prepararToolbar();
        // Preparar elementos UI
        prepararUI();

        return view;
    }

    private void prepararUI() {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mListView    = (ListView)view.findViewById(R.id.list_remark);
    }

    private void prepararToolbar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Registro Actividad");// Toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("DetailRemarkFragment");
    }

    /*    retornar lista de comentarios agrupada por fecha creacion.*/
    private void getDataSource(){
        LinkedHashMap<String, List<Object>> listHashMap = new LinkedHashMap<>();

        for (Remark remark : remarkList) {
            SubPlanning subPlanningTmp = getSubPlanning(remark.getIdSubPlanning());
            remark.setSubPlanning(subPlanningTmp);

            String dateString = DateFormat.format("dd/MM/yyyy", new Date(remark.getDate())).toString();
            //String dateString = DateFormat.format("MM/dd/yyyy", new Date(remark.getDate())).toString();
            if (!listHashMap.containsKey(dateString))
                listHashMap.put(dateString, new LinkedList<Object>());

            listHashMap.get(dateString).add(remark);
        }

        for (Bugs bugs : bugsList) {
            String dateString = DateFormat.format("dd/MM/yyyy", new Date(bugs.getDate())).toString();

            if (!listHashMap.containsKey(dateString))
                listHashMap.put(dateString, new LinkedList<Object>());

            listHashMap.get(dateString).add(bugs);
        }

        for (PromptTask promptTask : promptList) {
            String dateString = DateFormat.format("dd/MM/yyyy", new Date(promptTask.getDate())).toString();

            if (!listHashMap.containsKey(dateString))
                listHashMap.put(dateString, new LinkedList<Object>());

            listHashMap.get(dateString).add(promptTask);
        }

        Map<String, List<Object>> order = new TreeMap<String, List<Object>>(listHashMap);
        Map<String, List<Object>> order2 = new TreeMap<String, List<Object>>(Collections.reverseOrder());
        order2.putAll(order);

        ArrayList<Object> datas = new ArrayList<>();

        // Get a set of the entries
        Set<Map.Entry<String, List<Object>>> setMap = order2.entrySet();
        // Get an iterator
        Iterator<Map.Entry<String, List<Object>>> iteratorMap = setMap.iterator();
        // display all the elements
        while (iteratorMap.hasNext()){
            Map.Entry<String, List<Object>> entry = (Map.Entry<String, List<Object>>) iteratorMap.next();

            String key = entry.getKey();
            datas.add(key);

            List<Object> values = entry.getValue();
            for (int i = 0; i < values.size(); i++){
                Object obj = values.get(i);
                if(obj instanceof Remark){
                    Remark remark = (Remark) obj;
                    datas.add(remark);
                }if(obj instanceof Bugs){
                    Bugs bug = (Bugs) obj;
                    datas.add(bug);
                }else if (obj instanceof PromptTask){
                    PromptTask task = (PromptTask)obj;
                    datas.add(task);
                }
            }
        }

        mAdapter = new RequestAdapterNew(getActivity(), datas);
        mAdapter.notifyDataSetChanged();
        mListView.setAdapter(mAdapter);
    }

    private SubPlanning getSubPlanning(String id){
        SubPlanning subPlanning = subPlanningHashMap.get(id);

        return subPlanning;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    /*RemarkView*/
    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void resultRemark(List<Remark> datas) {
        //clear all list
        remarkList = new ArrayList<>();

        remarkList = datas;
        //getDataSource();
    }

    @Override
    public void resultSubPlanning(List<SubPlanning> datas) {
        subPlanningHashMap = new HashMap<String, SubPlanning>();

        for (SubPlanning subPlanning : datas) {
            if (!subPlanningHashMap.containsKey(subPlanning.getId())){
                subPlanningHashMap.put(subPlanning.getId(), subPlanning);
            }
        }
    }

    @Override
    public void resultBugs(List<Bugs> datas) {
        //clear all list
        bugsList = new ArrayList<>();

        bugsList = datas;
    }

    @Override
    public void resultPrompt(List<PromptTask> datas) {
        //clear all list
        promptList = new ArrayList<>();

        promptList = datas;
        getDataSource();
    }


    @Override
    public void showError(int resMsg) {

    }
}
