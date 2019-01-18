package standardsoft.com.coraje.ui.view.detailTaskModule.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.Remark;
import standardsoft.com.coraje.data.model.entities.SubPlanning;
import standardsoft.com.coraje.data.model.entities.User;
import standardsoft.com.coraje.ui.view.detailTaskModule.DetailTaskPresenter;
import standardsoft.com.coraje.ui.view.detailTaskModule.DetailTaskPresenterClass;
import standardsoft.com.coraje.ui.view.detailTaskModule.view.adapter.RequestAdapter;
import standardsoft.com.coraje.ui.view.taskModule.view.TaskActivity;
import standardsoft.com.coraje.utilies.CommonUtils;

public class DetailTaskActivity extends AppCompatActivity implements DetailTaskView{

    // Referencias UI
    private EditText edtRemark;
    private CoordinatorLayout contentMain;
    private ListView listRemark;
    ProgressBar progressBar;

    private String mIdSubPlanning;
    private String mIdPlanning;
    private String mTaskSubPlanning;
    private RequestAdapter mAdapter;

    private DetailTaskPresenter mPresenter;

    String mUserName;
    Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);

        args = new Bundle();
        args = getIntent().getExtras();

        if (args != null){
            mUserName = args.getString(User.NAME);
            mIdPlanning = args.getString(SubPlanning.ID_PLANNING);
            mIdSubPlanning = args.getString(SubPlanning.ID);
            mTaskSubPlanning = args.getString(SubPlanning.TASK);
        }

        mPresenter = new DetailTaskPresenterClass(this);
        mPresenter.onCreate();

        // Preparar elementos UI
        prepararUI();
        configToolbar();
        prepararFab();
        configAdapter(new ArrayList<Remark>());
    }

    private void configAdapter(List<Remark> remarkList) {
        ArrayList<Remark> arrayList = new ArrayList<>();

        for (Remark remark : remarkList) {
            arrayList.add(remark);

        }
        mAdapter = new RequestAdapter(this, arrayList);
        mAdapter.notifyDataSetChanged();
        listRemark.setAdapter(mAdapter);
    }

    private void prepararUI() {
        contentMain = (CoordinatorLayout)findViewById(R.id.contentMain);
        edtRemark = (EditText)findViewById(R.id.edt_remark);
        listRemark = (ListView)findViewById(R.id.list_of_remark);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void configToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(mTaskSubPlanning);
        toolbar.setSubtitle("DetailTaskActivity");
        setSupportActionBar(toolbar);
    }

    private void prepararFab() {
        FloatingActionButton fabRemark = (FloatingActionButton) findViewById(R.id.fab_remark);
        fabRemark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonUtils.validatePlanning(getBaseContext(), edtRemark)){
                    Remark remark = new Remark();
                    remark.setDescription(edtRemark.getText().toString());
                    remark.setUser(mUserName);

                    Date date = new Date();
                    long starDate = date.getTime();
                    remark.setDate(starDate);

                    mPresenter.addRemarkTask(mIdPlanning,mIdSubPlanning, remark);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume(mIdSubPlanning);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.item_update == item.getItemId()){

            Intent intentTask = new Intent(DetailTaskActivity.this , TaskActivity.class);
            intentTask.putExtra(SubPlanning.ID, mIdSubPlanning);
            intentTask.putExtra(SubPlanning.ID_PLANNING, mIdPlanning);
            intentTask.putExtra(SubPlanning.TASK_PLANNING, args.getString(SubPlanning.TASK_PLANNING));
            intentTask.putExtra(SubPlanning.TASK, mTaskSubPlanning);
            intentTask.putExtra(SubPlanning.ASSIGNEE, args.getString(SubPlanning.ASSIGNEE));
            intentTask.putExtra(SubPlanning.ESTIMATION, args.getInt(SubPlanning.ESTIMATION));
            intentTask.putExtra(SubPlanning.STATUS, args.getString(SubPlanning.STATUS));
            intentTask.putExtra(SubPlanning.DATE, args.getString(SubPlanning.DATE));
            intentTask.putExtra(SubPlanning.DESCRIPTION, args.getString(SubPlanning.DESCRIPTION));

            startActivity(intentTask);
        }

        return super.onOptionsItemSelected(item);
    }

    /*DetailTaskView*/
    @Override
    public void clearUIElements() {
        edtRemark.setText("");
    }

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
        configAdapter(datas);
    }

    @Override
    public void addRemarkTask() {
        Snackbar.make(contentMain, R.string.add_message_request, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onShowError(int resMsg) {

    }


}
