package edu.galileo.android.androidchat.domain;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

import edu.galileo.android.androidchat.entities.User;

/**
 * Created by ludwing on 5/11/16.
 */
public class FirebaseHelper {

    private DatabaseReference dataReference;
    private final static String SEPARATOR = "___";
    private final static String CHATS_PATH = "chats";
    private final static String USERS_PATH = "users";
    private final static String CONTACTS_PATH = "contacts";
    private final static String FIREBASE_URL =  "https://androidchat-f415f.firebaseio.com";

    //se trabaja como singleton por eso está esta clase interna
    public static class SingletonHolder {
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }

    public static FirebaseHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public FirebaseHelper() {
        this.dataReference = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getDataReference() {
        return dataReference;
    }

    /**
     * Devuelve el correo del usuario autenticado en este momento
     * @return
     */
    public String getAuthUserEmail() {
        FirebaseAuth authData = FirebaseAuth.getInstance();
        String email = null;

        if (authData != null) { //si el usuario ya inició sesión
            FirebaseUser usuario = authData.getCurrentUser();
            email = usuario.getEmail();
        }

        return email;
    }

    /**
     * Devuelve la referencia hacia los usuarios
     * @param email
     * @return
     */
    public DatabaseReference getUserReference(String email) {
        DatabaseReference userReference = null;

        if (email != null) {
            String emailkey = email.replace(".", "_");  //se reemplaza el punto porque firebase no permite varios caracteres en la ruta, entre ellos el punto
            userReference = dataReference.getRoot().child(USERS_PATH).child(emailkey);
        }

        return userReference;
    }

    /**
     * Retorna la referencia de MI usuario
     * @return
     */
    public DatabaseReference getMyUserReference() {
        return getUserReference(getAuthUserEmail());
    }

    /**
     * Obtiene las referencias de los contactos de un usuario
     * @param email
     * @return
     */
    public DatabaseReference getContactsReference(String email) {
        return getUserReference(email).child(CONTACTS_PATH);
    }

    /**
     * Obtiene las referencias de MI usuario
     * @return
     */
    public DatabaseReference getMyContactsReference() {
        return getContactsReference(getAuthUserEmail());
    }

    /**
     * Metodo para obtener la referencia de un contácto a partir del correo del usuario y del correo del contacto
     * @param mainEmail
     * @param childEmail
     * @return
     */
    public DatabaseReference getOneContactReference(String mainEmail, String childEmail) {
        String childKey = childEmail.replace(".", "_");
        return getUserReference(mainEmail).child(CONTACTS_PATH).child(childKey);
    }

    /**
     * Obtiene las referencias de datos de los chats
     * @param receiver: mail del receptor
     * @return
     */
    public DatabaseReference getChatsReference(String receiver) {
        String keySender = getAuthUserEmail().replace(".","_");
        String keyReceiver = receiver.replace(".","_");

        String keyChat = keySender + SEPARATOR + keyReceiver;

        if (keySender.compareTo(keyReceiver) > 0) {  //comparacion por orden alfabetico
            keyChat = keyReceiver + SEPARATOR + keySender;
        }
        return dataReference.getRoot().child(CHATS_PATH).child(keyChat);
    }

    /**
     * Cambia el estado online/offline de mi usuario
     * @param online
     */
    public void changeUserConnectionStatus(boolean online) {
        if (getMyUserReference() != null) {
            Map<String, Object> updates = new HashMap<String, Object>();
            updates.put("online",online); //online es el key del bit del estado de usuario
            getMyUserReference().updateChildren(updates); //actualizar el estado
            notifyContactsOfConnectionChange(online);  //notificar a mis contactos el cambio de estado
        }
    }

    /**
     * Notifica a los contáctos el cambio de estado
     * @param online
     */
    public  void notifyContactsOfConnectionChange(boolean online) {
        notifyContactsOfConnectionChange(online, false);
    }

    /**
     * Método para cerrar sesión
     */
    public void signOff() {
        notifyContactsOfConnectionChange(User.OFFLINE, true);
    }

    /**
     * Cambia el estatus para cambiar cuando se necesite o cuando se cierre sesión
     * @param online
     * @param signoff
     */
    public void notifyContactsOfConnectionChange(final boolean online, final boolean signoff) {
        final String myEmail = getAuthUserEmail();

        //agregar un listener de un solo valor
        getMyContactsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) { //recorre todos los contactos
                    String email = child.getKey();
                    DatabaseReference reference = getOneContactReference(email, myEmail);
                    reference.setValue(online);
                }

                //si se está cerrando sesión, se indica el cierre de sesión
                //DESPUES de haber notificado a los contactos que cerré sesión
                //de lo contrario no se podría notificar
                if (signoff) {
                    FirebaseAuth.getInstance().signOut();
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
            }
        });
    }


}
