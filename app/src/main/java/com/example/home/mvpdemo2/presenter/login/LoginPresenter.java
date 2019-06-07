package com.example.home.mvpdemo2.presenter.login;

import android.text.TextUtils;

import com.example.home.mvpdemo2.model.login.User;
import com.example.home.mvpdemo2.view.login.ILoginView;
import com.example.home.mvpdemo2.view.login.LoginActivity;

interface ILoginPresenter{
    void checkLogin(String username, String password);
}

public class LoginPresenter implements ILoginPresenter {
    ILoginView loginView;

    public LoginPresenter(LoginActivity loginActivity) {
        this.loginView = loginActivity;
    }

    public void LoginPresenter(ILoginView view){
        this.loginView = view;
    }

    @Override
    public void checkLogin(String username, String password) {
        Errors errors = new Errors();

        if(TextUtils.isEmpty(username)){
            errors.setUsername("Username is required");
        }

        if(TextUtils.isEmpty(password)){
            errors.setPassword("Password is required");
        }

        if(Errors.isEmpty(errors)){
            User u = new User(username, password);
            boolean isValid = User.checkLogin(u);

            if(isValid) {
                loginView.showLoginSuccess("Login success");
            } else {
                loginView.showLoginError("Login Error");
            }
        } else {
            String msg =  "";
            msg += errors.getUsername() == null ? "" : (errors.getUsername() + "\n");
            msg += errors.getPassword() == null ? "" : errors.getPassword();
            loginView.showLoginError(msg);
        }

    }
}

class Errors{
    private String username, password;

    public Errors(){}

    public Errors(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    static boolean isEmpty(Errors errors){
        return errors.username == null && errors.password == null;
    }
}

