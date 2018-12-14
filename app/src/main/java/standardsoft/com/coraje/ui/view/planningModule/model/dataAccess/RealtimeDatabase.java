package standardsoft.com.coraje.ui.view.planningModule.model.dataAccess;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.common.BasicErrorEventCallback;
import standardsoft.com.coraje.data.model.data_access.FirebaseRealtimeDatabaseAPI;
import standardsoft.com.coraje.data.model.entities.Planning;
import standardsoft.com.coraje.ui.view.planningModule.events.PlanningEvent;

/**
 *   ¿En que forma nos ayuda SRP(Single Responsibility Principle)?
 *       Auxiliar en definir que tipo de acciones en concreto va a tratar una clase.
 *       Cada clase o modulo debe de resolver un solo tipo de problema.
 *   ¿De las siguientes ventajas, ¿Cual es gracias a el Patrón Singleton?
 *       Obtener una única instancia de una clase para todo el proyecto.
 *       Garantizar que una clase sea instanciada solo una vez y que solo se tenga un acceso a ella.
 *
 *  Selecciona cual de los siguientes casos, serían un correcto nombramiento de acuerdo a lo
 *   explicado en la clase: Listeners vs Callbacks.
 *       Listener: Interface añadida a un método que permita estar escuchando todo el tiempo por un
 *       evento que podría o no, ocurrir.
 *       Callback: Interface instanciada dentro de un método que si o si, devolverá una respuesta.
 *   MVP
 *   Model: Es la parte responsable de manejar los datos. Su objetivo es conectarse a la fuentes
 *       de datos.
 *   Presenter: Es la parte intermedia entre la vista y el modelo, Su funcion es de controlar la
 *       logica de la aplicacion que necesita el interfaz de usuario.
 *   View: Aqui la logica se reduce considerablemente, ya que solo tiene la responsabilida de
 *       presentar los datos y de reaccionar antes los eventos de pulsacion que ejecute el usuario.
 */

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase() {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void addPlanning(Planning planning, final BasicErrorEventCallback callback){
        mDatabaseAPI.getPlanningReference().push().setValue(planning, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null){
                    callback.onSuccess();
                } else {
                    switch (databaseError.getCode()){
                        default:
                            callback.onError(PlanningEvent.ERROR_SERVER, 0);
                    }
                }

            }
        });
    }
}
