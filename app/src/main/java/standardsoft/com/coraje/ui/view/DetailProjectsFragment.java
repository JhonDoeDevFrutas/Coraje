package standardsoft.com.coraje.ui.view;


import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.Project;
import standardsoft.com.coraje.data.preferences.FirebaseReferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailProjectsFragment extends Fragment {

    // Referencias UI
    private View view;

    DatabaseReference mDbReference;//our database reference object

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail_projects, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Firebase Init
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        //getting the reference of node
        mDbReference = database.getReference(FirebaseReferences.PROJECT_REFERENCE);

        prepararFab();

        return view;
    }

    private void prepararFab() {
        FloatingActionButton fabDeveloper = (FloatingActionButton)view.findViewById(R.id.fab_project);
        fabDeveloper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Nuevo Proyecto");

        final EditText edtDescription = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        edtDescription.setLayoutParams(lp);
        alertDialog.setView(edtDescription); // Add edit text to alert dialog
        alertDialog.setIcon(R.drawable.ic_add);

        alertDialog.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                // Set value for new project
                //getting a unique id using push().getKey() method
                //it will create a unique id and we will use it as the Primary Key for our project
                String id = mDbReference.push().getKey();

                String description = edtDescription.getText().toString();

                Project project = new Project(id, description);

                //Saving
                mDbReference.child(id).setValue(project);
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

}
