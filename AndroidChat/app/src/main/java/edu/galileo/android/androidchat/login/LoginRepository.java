package edu.galileo.android.androidchat.login;

/**
 * Clase que interactua con firebase (es la unica clase que esta enterada de firebase)
 * Created by ludwin on 5/11/16.
 */
public interface LoginRepository {
    void signUp(String email, String password);
    void signIn(String email, String password);
    void checkSession();
}
