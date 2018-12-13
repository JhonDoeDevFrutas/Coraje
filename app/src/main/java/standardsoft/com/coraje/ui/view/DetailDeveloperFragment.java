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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.preferences.FirebaseReferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailDeveloperFragment extends Fragment {

    // Referencias UI
    private View view;

    DatabaseReference mDbReference;//our database reference object


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail_developer, container, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Firebase Init
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        //getting the reference of node
        mDbReference = database.getReference(FirebaseReferences.DEVELOPER_REFERENCE);

        prepararFab();
        return view;
    }

    private void prepararFab() {
        FloatingActionButton fabDeveloper = (FloatingActionButton)view.findViewById(R.id.fab_developer);
        fabDeveloper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        final EditText edtName,edtMovil;

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Nuevo Informatico");

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View add_developer = inflater.inflate(R.layout.add_new_user, null);

        edtName  = (EditText) add_developer.findViewById(R.id.edt_name);
        edtMovil  = (EditText) add_developer.findViewById(R.id.edt_movil);

        alertDialog.setView(add_developer);
        alertDialog.setIcon(R.drawable.ic_add);

        alertDialog.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                // Set value for new developer
                //getting a unique id using push().getKey() method
                //it will create a unique id and we will use it as the Primary Key for our developer
                String id = mDbReference.push().getKey();

                String name = edtName.getText().toString();
                String movil = edtMovil.getText().toString();

                Developer developer = new Developer(name, movil,id);

                //Saving
                mDbReference.child(id).setValue(developer);
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
