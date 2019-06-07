package com.example.home.mvpdemo2.view.todo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home.mvpdemo2.R;
import com.example.home.mvpdemo2.model.todo.Todo;
import com.example.home.mvpdemo2.presenter.todo.ITodosPresenter;
import com.example.home.mvpdemo2.adapter.TodoAdapter;
import com.example.home.mvpdemo2.presenter.todo.TodosPresenter;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class TodosActivity extends AppCompatActivity implements ITodoView {

    private ITodosPresenter todoPresenter;

    private ListView todoListview;
    private Button btnAddTodo;
    private EditText edtSearchTask;
    private Spinner spinnerlevelFilter;

    private TodoAdapter todoAdapter;
    private List<Todo> todoList;

    String currentSearchValue = "";
    int currentLevel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);

        todoPresenter = new TodosPresenter(this);

        btnAddTodo = findViewById(R.id.btnAddTodo);
        todoListview = findViewById(R.id.todoListview);
        edtSearchTask = findViewById(R.id.edtSearchTask);
        spinnerlevelFilter = findViewById(R.id.spinnerlevelFilter);

        final String[] levels = { "All", "Easy", "Medium", "Hard" };
        ArrayAdapter<String> levelsAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, levels);
        spinnerlevelFilter.setAdapter(levelsAdapter);

        spinnerlevelFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentLevel = position;
                List<Todo> list = todoPresenter.filterTodos(todoList, currentSearchValue, currentLevel);
                todoAdapter = new TodoAdapter(TodosActivity.this, R.layout.todo_resource, list);
                todoListview.setAdapter(todoAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddTodoEvent();
            }
        });



        edtSearchTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                System.out.println(s.toString());
                currentSearchValue = s.toString();
               List<Todo> list = todoPresenter.filterTodos(todoList, currentSearchValue, currentLevel);
               todoAdapter = new TodoAdapter(TodosActivity.this, R.layout.todo_resource, list);
               todoListview.setAdapter(todoAdapter);
            }
        });

        loadTodosFromDB();
    }

    public void loadTodosFromDB(){
        todoList = todoPresenter.getTodos();
        todoAdapter = new TodoAdapter(this, R.layout.todo_resource, todoList);
        todoListview.setAdapter(todoAdapter);

        currentLevel = 0;
        currentSearchValue = "";

        edtSearchTask.setText(currentSearchValue);
        spinnerlevelFilter.setSelection(currentLevel);
    }

    public void handleAddTodoEvent(){
        final Dialog dialog = new Dialog(TodosActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.todo_edit_dialog);

        Button btnSave = dialog.findViewById(R.id.btnSave);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        final EditText edtTodoName = dialog.findViewById(R.id.edtTodoName);
        final EditText edtTodoDesc = dialog.findViewById(R.id.edtTodoDesc);
        final RadioGroup radioGroupLevel = dialog.findViewById(R.id.radioGroupLevel);
        TextView txtHeader = dialog.findViewById(R.id.txtHeader);
        txtHeader.setText("Insert Task");

        final int[] level = {3};

        radioGroupLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioEasy:
                        level[0] = 1;
                        break;
                    case R.id.radioMedium:
                        level[0] = 2;
                        break;
                    case R.id.radioHard:
                        level[0] = 3;
                        break;
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtTodoName.getText().toString();
                String desc =  edtTodoDesc.getText().toString();
                Todo todo = new Todo(-1, name, desc, level[0]);
                todoPresenter.addTodo(todo);
                dialog.dismiss();
                Toast.makeText(TodosActivity.this, "Insert successfully", Toast.LENGTH_SHORT).show();
                loadTodosFromDB();
            }
        });

        dialog.show();
    }

    public void showEditDialog(final Todo todo){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.todo_edit_dialog);

        Button btnSave = dialog.findViewById(R.id.btnSave);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        final EditText edtTodoName = dialog.findViewById(R.id.edtTodoName);
        final EditText edtTodoDesc = dialog.findViewById(R.id.edtTodoDesc);

        final RadioGroup radioGroupLevel = dialog.findViewById(R.id.radioGroupLevel);

        RadioButton radioEasy = dialog.findViewById(R.id.radioEasy);
        RadioButton radioMedium = dialog.findViewById(R.id.radioMedium);
        RadioButton radioHard = dialog.findViewById(R.id.radioHard);

        int level = todo.getLevel();

        switch (level){
            case 1:
                radioEasy.setChecked(true);
                break;
            case 2:
                radioMedium.setChecked(true);
                break;
            case 3:
                radioHard.setChecked(true);
                break;
        }

        edtTodoName.setText(todo.getName());
        edtTodoDesc.setText(todo.getDescription());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                todo.setName(edtTodoName.getText().toString());
                todo.setDescription(edtTodoDesc.getText().toString());

                radioGroupLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.radioEasy:
                                todo.setLevel(1);
                                break;
                            case R.id.radioMedium:
                                todo.setLevel(2);
                                break;
                            case R.id.radioHard:
                                todo.setLevel(3);
                                break;
                        }
                    }
                });


                todoPresenter.updateTodo(todo);

                dialog.dismiss();

                Toast.makeText(getApplicationContext(), "Update successfully", Toast.LENGTH_SHORT).show();

                loadTodosFromDB();
            }
        });

        dialog.show();
    }

    public void showConfirmDeleteTodo(final Todo todo){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this);

        dialogDelete.setMessage("Are you sure to delete " + todo.getName());

        dialogDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                todoPresenter.deleteTodo(todo);
                Toast.makeText(TodosActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                loadTodosFromDB();
            }
        });

        dialogDelete.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialogDelete.show();
    }



}
