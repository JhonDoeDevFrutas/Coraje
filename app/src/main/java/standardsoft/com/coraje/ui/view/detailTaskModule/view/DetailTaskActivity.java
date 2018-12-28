package standardsoft.com.coraje.ui.view.detailTaskModule.view;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.Remark;
import standardsoft.com.coraje.data.model.entities.SubPlanning;
import standardsoft.com.coraje.ui.view.detailTaskModule.DetailTaskPresenter;
import standardsoft.com.coraje.ui.view.detailTaskModule.DetailTaskPresenterClass;
import standardsoft.com.coraje.ui.view.detailTaskModule.view.adapter.RequestAdapter;
import standardsoft.com.coraje.utilies.CommonUtils;

public class DetailTaskActivity extends AppCompatActivity implements DetailTaskView{

    // Referencias UI
    private EditText edtRemark;
    private CoordinatorLayout contentMain;
    private ListView listRemark;

    private String mIdSubPlanning;
    private RequestAdapter mAdapter;

    private DetailTaskPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);

        Bundle args = getIntent().getExtras();
        if (args != null){
            mIdSubPlanning = args.getString(SubPlanning.ID);
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
    }

    private void configToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

                    Date date = new Date();
                    long starDate = date.getTime();
                    remark.setDate(starDate);

                    mPresenter.addRemarkTask(mIdSubPlanning, remark);
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

    /*DetailTaskView*/
    @Override
    public void clearUIElements() {
        edtRemark.setText("");
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

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
