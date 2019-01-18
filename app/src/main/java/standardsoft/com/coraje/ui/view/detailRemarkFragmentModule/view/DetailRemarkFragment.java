package standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.view;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.Remark;
import standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.DetailRemarkPresenter;
import standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.DetailRemarkPresenterClass;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailRemarkFragment extends Fragment implements RemarkView{

    // Referencias UI
    private View view;
    ProgressBar progressBar;

    private DetailRemarkPresenter mPresenter;

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
    }


    private void prepararToolbar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Registro Actividad");// Toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("DetailRemarkFragment");
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    /*RemarkView*/
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void resultSubPlanning(List<Remark> datas) {

    }

    @Override
    public void showError(int resMsg) {

    }
}
