package net.windowsv8.mycat;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Windowsv8 on 23/08/2017.
 */

public class LoginActivity extends AppCompatActivity{

    EditText editTextUsername,editTextPassword;
    Context context = LoginActivity.this;
    AppCompatButton buttonLogin, buttonRegister;
    ProgressDialog pDialog;
    Toolbar toolbar;
    TextView textViewLoginAdmin;
    private TextInputLayout textInputLayout1,textInputLayout2;

    SharedPreferences sharedPreferences;
    Boolean session = false;
    public String id, username1;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";
    public final static String TAG_USERNAME = "username";
    public final static String TAG_ID = "id";
    public final static String TAG_VERIF = "verifikasi";
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int keluar = 2000;
    private long mbackpressed;

    @Override
    public void onBackPressed() {
        if (mbackpressed + keluar > System.currentTimeMillis()){
            finishAffinity();
            super.onBackPressed();
            return;
        }else {
            Toast.makeText(getApplicationContext(), "tekan kembali untuk keluar", Toast.LENGTH_SHORT).show();
        }
        mbackpressed = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = LoginActivity.this;
        pDialog = new ProgressDialog(context);
        editTextUsername = findViewById(R.id.editTextUsernameUsername1);
        editTextPassword = findViewById(R.id.hide_show_edittext_password);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        textViewLoginAdmin = findViewById(R.id.textViewLoginAdmin);
        textViewLoginAdmin.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        textInputLayout1 = findViewById(R.id.wrapper1);
        textInputLayout2 = findViewById(R.id.wrapper2);

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
                startActivity(new Intent(LoginActivity.this,LoginAdminActivity.class));
            }
        });
    }

    private void login(){
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            editTextUsername.setError("Silahkan masukkan username anda");
        }
        if(TextUtils.isEmpty(password)){
            editTextPassword.setError("Silahkan masukkan password anda");
        }
        else{
            pDialog.setMessage("Login Process ...");
            ShowDialog();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject json = new JSONObject(response);
                                int success = json.getInt("success");

//                                if (response.contains(Config.LOGIN_SUCCESS)) {
                                if (success == 1){
                                    String verifikasi = String.valueOf(json.getString(TAG_VERIF));
                                    Log.e(TAG,"verifikasi" + verifikasi);
                                    if (verifikasi.equals("1")){
                                        id = json.getString(TAG_ID);
                                        username1 = json.getString(TAG_USERNAME);

                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean(session_status, true);
                                        editor.putString(TAG_ID,id);
                                        editor.putString(TAG_USERNAME,username1);
                                        editor.commit();
                                        hideDialog();
                                        gotoActivity();
                                    }else {
                                        MDToast.makeText(LoginActivity.this, "Silahkan Melakukan Verifikasi Akun", MDToast.LENGTH_SHORT, MDToast.TYPE_INFO).show();
                                        hideDialog();
                                        keyboard();
                                    }

                                } else {
                                    hideDialog();
                                    MDToast.makeText(getApplicationContext(), "Username atau Password Salah", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                                    keyboard();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }


    }

    private void keyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void gotoActivity(){
        Intent ii = new Intent(this, HomeActivity.class);
        ii.putExtra(TAG_ID, id);
        ii.putExtra(TAG_USERNAME, username1);
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

}
