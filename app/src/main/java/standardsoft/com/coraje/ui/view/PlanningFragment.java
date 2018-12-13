package standardsoft.com.coraje.ui.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.preferences.FirebaseReferences;
import standardsoft.com.coraje.ui.view.planningModule.view.PlanningActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanningFragment extends Fragment {

    // Referencias UI
    private View view;

    DatabaseReference mDbReference;//our database reference object

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_planning, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Firebase Init
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        //getting the reference of node
        mDbReference = database.getReference(FirebaseReferences.DEVELOPER_REFERENCE);

        prepararFab();

        return view;
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
}
