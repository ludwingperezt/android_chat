package edu.galileo.android.androidchat.login;

/**
 * Created by ludwin on 5/11/16.
 */
public class LoginInteractorImpl implements LoginInteractor {

    private LoginRepository loginRepository;

    public LoginInteractorImpl() {
        loginRepository = new LoginRepositoryImpl();
    }

    @Override
    public void checkSession() {
        loginRepository.checkSession();
    }

    @Override
    public void doSingUp(String email, String password) {
        loginRepository.signUp(email, password);
    }

    @Override
    public void doSingIn(String email, String password) {
        loginRepository.signIn(email, password);
    }
}
