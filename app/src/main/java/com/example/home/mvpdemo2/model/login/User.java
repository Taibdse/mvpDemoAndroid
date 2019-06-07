package com.example.home.mvpdemo2.model.login;

public class User {
    String username, password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {}

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

    public static boolean checkLogin(User user){
        if(user.getUsername().equals("ductai") && user.getPassword().equals("123456")) return true;
        return false;
    }
}

