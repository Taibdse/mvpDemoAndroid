package com.example.home.mvpdemo2.presenter.todo;

import android.content.Context;

import com.example.home.mvpdemo2.model.todo.Todo;
import com.example.home.mvpdemo2.model.todo.TodoModel;
import com.example.home.mvpdemo2.view.todo.ITodoView;

import java.util.ArrayList;
import java.util.List;

public class TodosPresenter implements ITodosPresenter {

    TodoModel todoModel;
    ITodoView todoView;
    Todo currentTodo;

    public TodosPresenter(ITodoView todoView) {
        this.todoView = todoView;
        this.todoModel = new TodoModel(todoView);
    }

    @Override
    public List<Todo> getTodos() {
        return todoModel.getAllTodos();
    }

    @Override
    public Todo getTodo(int id) {
        return todoModel.getTodo(id);
    }

    @Override
    public void addTodo(Todo todo) {
        todoModel.addTodo(todo);
    }

    @Override
    public void deleteTodo(Todo todo) {
        todoModel.deleteTodo(todo);
    }

    @Override
    public void updateTodo(Todo todo) {
        todoModel.updateTodo(todo);
    }

    @Override
    public List<Todo> filterTodos(List<Todo> todoList, String searchValue, int level) {
        List<Todo> list = new ArrayList<>();

//        if(searchValue.trim().length() == 0 && level == 0) return todoList;

        if(level != 0){
            for(Todo todo: todoList){
                if(todo.getLevel() == level){
                    list.add(todo);
                }
            }
        } else {
            list = todoList;
        }

        if(searchValue.trim().length() != 0) {
            for(Todo todo: list){
                String name = todo.getName();
                if(name.toLowerCase().indexOf(searchValue.toLowerCase()) < 0){
                    list.remove(todo);
                }
            }
        }
        return list;
    }


}
