package standardsoft.com.coraje.ui.view.planningModule.model.dataAccess;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.common.BasicErrorEventCallback;
import standardsoft.com.coraje.data.model.data_access.FirebaseRealtimeDatabaseAPI;
import standardsoft.com.coraje.data.model.entities.Customer;
import standardsoft.com.coraje.data.model.entities.Developer;
import standardsoft.com.coraje.data.model.entities.Planning;
import standardsoft.com.coraje.data.model.entities.Project;
import standardsoft.com.coraje.ui.view.planningModule.events.PlanningEvent;

/**
 * ¿En que forma nos ayuda SRP(Single Responsibility Principle)?
 * Auxiliar en definir que tipo de acciones en concreto va a tratar una clase.
 * Cada clase o modulo debe de resolver un solo tipo de problema.
 * ¿De las siguientes ventajas, ¿Cual es gracias a el Patrón Singleton?
 * Obtener una única instancia de una clase para todo el proyecto.
 * Garantizar que una clase sea instanciada solo una vez y que solo se tenga un acceso a ella.
 * <p>
 * Selecciona cual de los siguientes casos, serían un correcto nombramiento de acuerdo a lo
 * explicado en la clase: Listeners vs Callbacks.
 * Listener: Interface añadida a un método que permita estar escuchando todo el tiempo por un
 * evento que podría o no, ocurrir.
 * Callback: Interface instanciada dentro de un método que si o si, devolverá una respuesta.
 * MVP
 * Model: Es la parte responsable de manejar los datos. Su objetivo es conectarse a la fuentes
 * de datos.
 * Presenter: Es la parte intermedia entre la vista y el modelo, Su funcion es de controlar la
 * logica de la aplicacion que necesita el interfaz de usuario.
 * View: Aqui la logica se reduce considerablemente, ya que solo tiene la responsabilida de
 * presentar los datos y de reaccionar antes los eventos de pulsacion que ejecute el usuario.
 */

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;
    private ValueEventListener mPlanningValueEventListener;

    public RealtimeDatabase() {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void addPlanning(Planning planning, final BasicErrorEventCallback callback) {
        mDatabaseAPI.getPlanningReference().push().setValue(planning, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    callback.onSuccess();
                } else {
                    switch (databaseError.getCode()) {
                        default:
                            callback.onError(PlanningEvent.ERROR_SERVER, 0);
                    }
                }
            }
        });
    }

    public void  updatePlanning(Planning planning, final BasicErrorEventCallback callback){
        mDatabaseAPI.getPlanningReference().child(planning.getId()).setValue(planning)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(PlanningEvent.ERROR_SERVER, 0);
                    }
                });
    }

    public void subscribeToProject(final PlanningEventListener listener) {
        mDatabaseAPI.getProjectsReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Project> projectsList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    projectsList.add(getProject(postSnapshot));
                }

                Collections.sort(projectsList, Project.Comparators.DESCRIPTION);
                listener.onDataChangeProject(projectsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                switch (databaseError.getCode()) {
                    case DatabaseError.PERMISSION_DENIED:
                        listener.onError(R.string.error_permission_denied);
                        break;
                    default:
                        listener.onError(R.string.error_server);
                }

            }
        });
    }

    public void subscribeToCustomer(final PlanningEventListener listener) {
        mDatabaseAPI.getCustomersReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Customer> customerList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    customerList.add(getCustomer(postSnapshot));
                }

                Collections.sort(customerList, Customer.Comparators.NAME); // order by name
                listener.onDataChange(customerList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                switch (databaseError.getCode()) {
                    case DatabaseError.PERMISSION_DENIED:
                        listener.onError(R.string.error_permission_denied);
                        break;
                    default:
                        listener.onError(R.string.error_server);
                }

            }
        });
    }

    public void subscribeToDeveloper(final PlanningEventListener listener) {
        mDatabaseAPI.getUserReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Developer> developerList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    developerList.add(getDeveloper(postSnapshot));
                }

                Collections.sort(developerList, Developer.Comparators.name);
                listener.onDataDeveloper(developerList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                switch (databaseError.getCode()) {
                    case DatabaseError.PERMISSION_DENIED:
                        listener.onError(R.string.error_permission_denied);
                        break;
                    default:
                        listener.onError(R.string.error_server);
                }

            }
        });
    }


    public void unsubscribeToCustomer(){
        if (mPlanningValueEventListener != null){
            mDatabaseAPI.getCustomersReference().removeEventListener(mPlanningValueEventListener);
        }
    }
    public void unsubscribeToProject(){
        if (mPlanningValueEventListener != null){
            mDatabaseAPI.getProjectsReference().removeEventListener(mPlanningValueEventListener);
        }
    }
    public void unsubscribeToDeveloper(){
        if (mPlanningValueEventListener != null){
            mDatabaseAPI.getDevelopersReference().removeEventListener(mPlanningValueEventListener);
        }
    }

    private Customer getCustomer(DataSnapshot dataSnapshot) {
        Customer customer = dataSnapshot.getValue(Customer.class);

        return customer;
    }
    private Developer getDeveloper(DataSnapshot dataSnapshot) {
        Developer developer = dataSnapshot.getValue(Developer.class);

        return developer;
    }

    private Project getProject(DataSnapshot dataSnapshot) {
        Project project = dataSnapshot.getValue(Project.class);

        return project;
    }

}
