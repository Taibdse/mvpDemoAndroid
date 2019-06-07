package com.example.home.mvpdemo2.model.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.home.mvpdemo2.view.todo.ITodoView;

import java.util.ArrayList;
import java.util.List;


interface ITodoModel{
    void addTodo(Todo todo);
    Todo getTodo(int id);
    List<Todo> getAllTodos();
    int updateTodo(Todo todo);
    int deleteTodo(Todo todo);
    int getTodosCount();
}

public class TodoModel extends SQLiteOpenHelper implements ITodoModel{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "demo";
    private static final String TABLE_TODOS = "todos";
    private static final String KEY_ID = "id";
//    private static final String KEY_NAME = "name";
//    private static final String KEY_PH_NO = "phone_number";

    public TodoModel(ITodoView todoView) {
        super((Context) todoView, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TODOS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + "name TEXT,"
                + "description TEXT, " + "level INTEGER"  + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    @Override
    public void addTodo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", todo.getName());
        values.put("description", todo.getDescription());
        values.put("level", todo.getLevel());

        db.insert(TABLE_TODOS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    @Override
    public Todo getTodo(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[] { KEY_ID, "name", "description", "level" };
        String selection = KEY_ID + "=?";
        Cursor cursor = db.query(TABLE_TODOS, columns, selection, new String[] {id + ""}, null, null, null, null);
        Todo todo = null;
        if (cursor != null){
            cursor.moveToFirst();

            String name = cursor.getString(cursor.getColumnIndex("name"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            int level = cursor.getInt(cursor.getColumnIndex("level"));

            todo = new Todo(id, name, description, level);
        }
         cursor.close();
         db.close();
         return todo;
    }

    @Override
    public List<Todo> getAllTodos() {
        List<Todo> todoList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TODOS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                int level = cursor.getInt(cursor.getColumnIndex("level"));

                Todo todo = new Todo(id, name, description, level);

                todoList.add(todo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return contact list
        return todoList;
    }

    // code to update the single contact
    @Override
    public int updateTodo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", todo.getName());
        values.put("description", todo.getDescription());
        values.put("level", todo.getLevel());

        String whereClause = KEY_ID + "=?";

        // updating row
        int result = db.update(TABLE_TODOS, values, whereClause, new String[] { todo.getId() + "" });
        db.close();
        return result;
    }

    // Deleting single contact
    @Override
    public int deleteTodo(Todo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = KEY_ID + "=?";
        int result = db.delete(TABLE_TODOS, whereClause, new String[] { String.valueOf(todo.getId()) });
        db.close();
        return result;
    }

    // Getting contacts Count
    @Override
    public int getTodosCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TODOS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        cursor.close();
        db.close();

        // return count
        return cursor.getCount();
    }

}
