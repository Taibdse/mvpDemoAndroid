package com.example.home.mvpdemo2.view.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.home.mvpdemo2.R;
import com.example.home.mvpdemo2.presenter.login.LoginPresenter;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements ILoginView {
    LoginPresenter loginPresenter;

    Button btnLogin;
    EditText edtUsername, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPresenter = new LoginPresenter(this);

        mapToView();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                System.out.println(username + " " + password);
                loginPresenter.checkLogin(username, password);
            }
        });
    }

    public void mapToView(){
        btnLogin = findViewById(R.id.btnLogin);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
    }

    @Override
    public void showLoginSuccess(String msg) {
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginError(String msg) {
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
