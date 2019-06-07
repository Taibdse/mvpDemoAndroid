package com.example.home.mvpdemo2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.home.mvpdemo2.R;
import com.example.home.mvpdemo2.model.todo.Todo;
import com.example.home.mvpdemo2.view.main.MainActivity;
import com.example.home.mvpdemo2.view.todo.TodosActivity;

import java.util.List;

public class TodoAdapter extends BaseAdapter {

    private TodosActivity context;
    private int layout;
    private List<Todo> todoList;

    public TodoAdapter(TodosActivity context, int layout, List<Todo> todoList) {
        this.context = context;
        this.layout = layout;
        this.todoList = todoList;
    }

    @Override
    public int getCount() {
        return todoList.size();
    }

    @Override
    public Object getItem(int position) {
        return todoList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return todoList.get(position).getId();
    }

    private class ViewHolder{
        TextView txtTodoName, txtTodoDesc, txtLevel;
        ImageView imgDelete, imgEdit;
    }

    @SuppressLint("ResourceType")
    public ViewHolder setHolder(View convertView){
        ViewHolder holder = new ViewHolder();
        holder.txtTodoName = convertView.findViewById(R.id.txtTodoName);
        holder.txtTodoDesc = convertView.findViewById(R.id.txtTodoDesc);
        holder.txtLevel = convertView.findViewById(R.id.txtLevel);
        holder.imgDelete = convertView.findViewById(R.id.imgDelete);
        holder.imgEdit = convertView.findViewById(R.id.imgEdit);

        holder.imgDelete.setImageResource(R.raw.delete);
        holder.imgEdit.setImageResource(R.raw.check);
        return holder;

    }

    public void bindEventToView(ViewHolder holder, final Todo todo){
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.showConfirmDeleteTodo(todo);
            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.showEditDialog(todo);
            }
        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        int todoPosition = position;
        final Todo todo = todoList.get(position);

        if(convertView == null){
            convertView = inflater.inflate(layout, null);
            holder = setHolder(convertView);

            bindEventToView(holder, todo);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtTodoName.setText(todo.getName());
        holder.txtTodoDesc.setText(todo.getDescription());

        String level = "";

        if(todo.getLevel() == 1) level = "Easy";
        else if(todo.getLevel() == 2) level = "Medium";
        else if(todo.getLevel() == 3) level = "Hard";

        holder.txtLevel.setText(level);

        return convertView;
    }
}
