package com.example.home.mvpdemo2.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.home.mvpdemo2.R;
import com.example.home.mvpdemo2.view.login.LoginActivity;
import com.example.home.mvpdemo2.view.todo.TodosActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnShowLoginActivity, btnShowTodoActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShowLoginActivity = findViewById(R.id.btnShowLoginActivity);
        btnShowTodoActivity = findViewById(R.id.btnShowTodoActivity);

        btnShowLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnShowTodoActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TodosActivity.class);
                startActivity(intent);
            }
        });
    }

}
