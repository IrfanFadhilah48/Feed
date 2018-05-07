package net.simplifiedcoding.myfeed;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.auth.api.zza.ii;

/**
 * Created by Windowsv8 on 23/08/2017.
 */

public class LoginActivity extends AppCompatActivity{

    EditText editTextUsername;
    Context context;
    AppCompatButton buttonLogin, buttonRegister;
    ProgressDialog pDialog;
    EditText editTextPassword;
    Toolbar toolbar;
    TextView textViewLoginAdmin;
    private TextInputLayout textInputLayout1,textInputLayout2;
    private static int REQUEST_CODE_ASK_PERMISSIONS_STORAGE = 1;
    private static int REQUEST_CODE_ASK_PERMISSIONS_CAMERA = 2;

    private User user;


    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERNAME = "username";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";


    SharedPreferences sharedPreferences;
    Boolean session = false;
    String id, username1;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";
    public final static String TAG_USERNAME = "username";
    public final static String TAG_ID = "id";
    private static final String TAG = LoginActivity.class.getSimpleName();

//    private PermissionHelper permissionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

//        permissionHelper = new PermissionHelper(this);
        context = LoginActivity.this;
        pDialog = new ProgressDialog(context);
        editTextUsername = (EditText)findViewById(R.id.editTextUsernameUsername1);
        editTextPassword = (EditText)findViewById(R.id.hide_show_edittext_password);
        buttonLogin = (AppCompatButton)findViewById(R.id.buttonLogin);
        buttonRegister = (AppCompatButton)findViewById(R.id.buttonRegister);
        toolbar = findViewById(R.id.toolbarLogin);
        textViewLoginAdmin = findViewById(R.id.textViewLoginAdmin);
        textInputLayout1 = findViewById(R.id.wrapper1);
        textInputLayout2 = findViewById(R.id.wrapper2);

        textViewLoginAdmin.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("MyCat");


        //request permission
        if (isStorageGaranted() && isCameraGaranted()){

        }else {

        }
//        checkAndRequestPermission();


        user = new User(this);

        //session Manager
        sharedPreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedPreferences.getBoolean(session_status, false);
        id = sharedPreferences.getString(TAG_ID, null);
        username1 = sharedPreferences.getString(TAG_USERNAME, null);

        if(session){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra(TAG_ID,id);
            intent.putExtra(TAG_USERNAME,username1);
            finish();
            startActivity(intent);
        }
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        textViewLoginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,"berhasil masuk", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this,LoginAdminActivity.class));
            }
        });
    }



    private void login(){
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        username1 = username;

        if(TextUtils.isEmpty(username)){
//            editTextUsername.setError("Silahkan masukkan username anda");
            textInputLayout1.setError("Silahkan Masukkan Username");
        }
        else{
            pDialog.setMessage("Login Process ...");
            ShowDialog();

//        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(USERNAME, username);
//        editor.apply();
//        editor.commit();
            user.setUsername(username);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            try{
//                                JSONObject json = new JSONObject(response);
                            Log.e(TAG,"hasil response : " +response);
                            if (response.contains(Config.LOGIN_SUCCESS)) {

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean(session_status, true);
                                editor.putString(TAG_ID,id);
                                editor.putString(TAG_USERNAME,username1);
                                editor.commit();
//                                    JSONObject userJSON = json.getJSONObject("user");
//                                    User user = new User(
//                                            userJSON.getInt("id"),
//                                            userJSON.getString("username"),
//                                            userJSON.getString("password")
//                                    );
//                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                hideDialog();
                                gotoActivity();
                            } else {
                                hideDialog();
                                Toast.makeText(context, "Username atau Password Salah", Toast.LENGTH_SHORT).show();
                            }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideDialog();
                            Toast.makeText(context,"Kesalahan Sambungan",Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put(Config.KEY_USERNAME,username);
                    params.put(Config.KEY_PASSWORD,password);
                    return params;
                }
            };
            //Volley.newRequestQueue(this).add(stringRequest);
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }
        if(TextUtils.isEmpty(password)){
//            editTextPassword.setError("Silahkan masukkan password anda");
            textInputLayout2.setError("Silahkan Masukkan Password");
        }

//        pDialog.setMessage("Login Process ...");
//        ShowDialog();
//
////        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
////        SharedPreferences.Editor editor = sharedPreferences.edit();
////        editor.putString(USERNAME, username);
////        editor.apply();
////        editor.commit();
//        user.setUsername(username);
//
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
////                            try{
////                                JSONObject json = new JSONObject(response);
//
//                                if (response.contains(Config.LOGIN_SUCCESS)) {
////                                    JSONObject userJSON = json.getJSONObject("user");
////                                    User user = new User(
////                                            userJSON.getInt("id"),
////                                            userJSON.getString("username"),
////                                            userJSON.getString("password")
////                                    );
////                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
//
//                                    hideDialog();
//                                    gotoActivity();
//                                } else {
//                                    hideDialog();
//                                    Toast.makeText(context, "Username atau Password Salah", Toast.LENGTH_SHORT).show();
//                                }
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            hideDialog();
//                            Toast.makeText(context,"Kesalahan Sambungan",Toast.LENGTH_LONG).show();
//                        }
//                    }){
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String,String> params = new HashMap<>();
//                    params.put(Config.KEY_USERNAME,username);
//                    params.put(Config.KEY_PASSWORD,password);
//                    return params;
//                }
//            };
//            //Volley.newRequestQueue(this).add(stringRequest);
//            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }



    private void gotoActivity(){
        //startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        Intent ii = new Intent(this, HomeActivity.class);
//        ii.putExtra("username",Config.KEY_USERNAME);
//        ii.putExtra("password",Config.KEY_PASSWORD);
        ii.putExtra(TAG_ID, id);
        ii.putExtra(TAG_USERNAME, username1);
        //finish();
        startActivity(ii);
        finish();
    }

    private void ShowDialog(){
        if(!pDialog.isShowing()){


            pDialog.show();
        }
    }

    private void hideDialog(){
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }
    }


    private boolean isCameraGaranted(){
        if (Build.VERSION.SDK_INT >= 23){
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_PERMISSIONS_CAMERA);
                return false;
            }
        }
        else {
            return true;
        }
    }

    private boolean isStorageGaranted(){
        if (Build.VERSION.SDK_INT >= 23){
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED){
                return true;
            }
            else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS_STORAGE);
                return false;
            }
        }
        else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        permissionHelper.onRequestCallback(requestCode,permissions,grantResults);
        try{
            if (requestCode == REQUEST_CODE_ASK_PERMISSIONS_STORAGE){
                if (grantResults.length > 0){
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        isCameraGaranted();
                    }else {
                        //denied permission
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Grant Permission")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
            }

            if (requestCode == REQUEST_CODE_ASK_PERMISSIONS_STORAGE){
                if (grantResults.length > 0){
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Grant Permission")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();

                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    private boolean checkAndRequestPermission() {
//        permissionHelper.permissionListener(new PermissionHelper.PermissionListener() {
//            @Override
//            public void onPermissionCheckDone() {
//
//            }
//        });
//        permissionHelper.checkAndRequestPermission();
//        return true;
//    }

}
