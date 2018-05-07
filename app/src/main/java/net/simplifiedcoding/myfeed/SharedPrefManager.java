package net.simplifiedcoding.myfeed;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Windowsv8 on 23/12/2017.
 */

public class SharedPrefManager {
//    private static final String SHARED_PREF = "usernamesharedref";
//    private static final String KEY_ID = "keyid";
//    private static final String KEY_USERNAME = "keyusername";
//    private static final String KEY_PASSWORD = "keypassword";
//
//    private static SharedPrefManager mInstance;
//    private static Context mContext;
//
//    private SharedPrefManager(Context context){
//        mContext = context;
//    }
//
//    public static synchronized SharedPrefManager getInstance(Context context){
//        if (mInstance == null){
//            mInstance = new SharedPrefManager(context);
//        }
//        return mInstance;
//    }
//
//    public void userLogin(User user){
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
////        editor.putInt(KEY_ID, user.getId());
////        editor.putString(KEY_USERNAME, user.getUsername());
////        editor.putString(KEY_PASSWORD, user.getPassword());
//        editor.apply();
//    }
//
//    public boolean isLoggedIn() {
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(KEY_USERNAME, null) != null;
//    }
//
//
//    public User getUser(){
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
//        return new User(
//                sharedPreferences.getInt(KEY_ID, -1),
//                sharedPreferences.getString(KEY_USERNAME, null),
//                sharedPreferences.getString(KEY_PASSWORD, null)
//        );
//    }
//
//    public void logout(){
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.apply();
//        mContext.startActivity(new Intent(mContext, LoginActivity.class));
//    }
}
