package com.example.home.mvpdemo2.presenter.todo;

import android.content.Context;

import com.example.home.mvpdemo2.model.todo.Todo;

import java.util.List;

public interface ITodosPresenter{
    List<Todo> getTodos();
    Todo getTodo(int id);
    void addTodo(Todo todo);
    void deleteTodo(Todo todo);
    void updateTodo(Todo todo);
    List<Todo> filterTodos(List<Todo> todoList, String searchValue, int level);
}
