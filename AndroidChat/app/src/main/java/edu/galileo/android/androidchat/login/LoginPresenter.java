package edu.galileo.android.androidchat.login;

import edu.galileo.android.androidchat.login.events.LoginEvent;

/**
 * Created by ludwin on 5/11/16.
 */
public interface LoginPresenter {

    void onCreate();
    void onDestroy();  //para evitar memory leak al destruir la vista, destruir la variable asignada al presentador
    void checkForAutenthenticatedUser();
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);
    void onEventMainThread(LoginEvent event);
}
