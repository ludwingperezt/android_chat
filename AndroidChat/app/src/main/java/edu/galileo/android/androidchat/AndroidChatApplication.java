package edu.galileo.android.androidchat;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ludwin on 5/11/16.
 */
public class AndroidChatApplication extends Application {

    public void onCreate() {
        super.onCreate();
        setupFirebase();
    }

    private void setupFirebase() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);  //agrega soporte para caracteristicas offline
    }
}
