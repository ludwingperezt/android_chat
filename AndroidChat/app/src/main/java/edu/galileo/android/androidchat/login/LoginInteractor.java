package edu.galileo.android.androidchat.login;

/**
 * Created by ludwin on 5/11/16.
 */
public interface LoginInteractor {
    void checkSession();
    void doSingUp(String email, String password);
    void doSingIn(String email, String password);
}
