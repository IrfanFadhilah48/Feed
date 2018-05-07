package net.simplifiedcoding.myfeed;

import android.content.Context;
import android.content.SharedPreferences;

import static android.R.attr.id;

/**
 * Created by Windowsv8 on 23/12/2017.
 */
public class User {

//    private int id;
//    private String username, password;
    private String KEY_USERNAME = "username";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public User(Context context){
        String PREFS_NAME = "UserPref";
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setUsername(String username){
        editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public String getUsername(){
        return  sharedPreferences.getString(KEY_USERNAME, null);
    }

//    public int getId() {
//        return id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public User(int id, String username, String password){
//        this.id = id;
//        this.username = username;
//        this.password = password;
//
//
//    }
}
