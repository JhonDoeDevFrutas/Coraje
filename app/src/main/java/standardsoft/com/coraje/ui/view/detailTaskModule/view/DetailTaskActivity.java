package standardsoft.com.coraje.ui.view.detailTaskModule.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.ui.view.detailTaskModule.DetailTaskPresenter;
import standardsoft.com.coraje.ui.view.detailTaskModule.DetailTaskPresenterClass;

public class DetailTaskActivity extends AppCompatActivity implements DetailTaskView{

    // Referencias UI
    private EditText edtNote;
    private ImageButton btnSend;

    private DetailTaskPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);

        mPresenter = new DetailTaskPresenterClass(this);
        mPresenter.onCreate();

        // Preparar elementos UI
        prepararUI();
        configToolbar();
        prepararFab();
    }

    private void prepararUI() {
        edtNote = (EditText)findViewById(R.id.edt_note);
        btnSend = (ImageButton)findViewById(R.id.btn_send);
    }

    private void configToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void prepararFab() {
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
    }

    /*DetailTaskView*/
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void addRemarkTask() {

    }

    @Override
    public void onShowError(int resMsg) {

    }


}
