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
    public static final String KEY_USERNAME = "username";
    public static final String KEY_ID = "id";
    public static final String KEY_LOGIN = "login";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "UserPref";

    public User(Context context){
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setUsername(String KEY_USERNAME, String value){
        editor.putString(KEY_USERNAME, value);
        editor.commit();
    }

    public String getKEY_ID() {
        return sharedPreferences.getString(KEY_ID, "");
    }

    public void setKEY_ID(String KEY_ID,String value) {
        editor.putString(KEY_ID, value);
        editor.commit();
    }

    public Boolean getKEY_LOGIN() {
        return sharedPreferences.getBoolean(KEY_LOGIN, false);
    }

    public void setKEY_LOGIN(String KEY_LOGIN, boolean value) {
        editor.putBoolean(KEY_LOGIN, value);
        editor.commit();

    }

    public String getUsername(){
        return  sharedPreferences.getString(KEY_USERNAME, "");
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
