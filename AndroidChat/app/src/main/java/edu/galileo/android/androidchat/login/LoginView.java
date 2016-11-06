package edu.galileo.android.androidchat.login;

/**
 * Manejo de login
 * Created by ludwin on 5/11/16.
 */
public interface LoginView {

    //UI
    void enableInputs();
    void disableInputs();
    void showProgress();
    void hideProgress();

    //login y registro
    void handleSignUp();
    void handleSignIn();

    //funciones especificas de login
    void navigateToMainScreen();  //en caso de login exitoso
    void loginError(String error); //en caso de error

    //funciones de registro
    void newUserSuccess();  //usuario nuevo creado con exito
    void newUserError(String error);  //error al crear el nuevo usuario
}
