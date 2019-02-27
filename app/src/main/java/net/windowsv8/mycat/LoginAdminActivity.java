package net.windowsv8.mycat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Windowsv8 on 05/05/2018.
 */

public class LoginAdminActivity extends AppCompatActivity {
    private EditText editTextUsernameAdmin,editTextPasswordAdmin;
    private AppCompatButton buttonLoginAdmin;
    private RequestQueue requestQueue;
    private TextInputLayout textInputLayout1,textInputLayout2;
    ProgressDialog pDialog;
    Context context;
    private static final String TAG = LoginAdminActivity.class.getSimpleName();

    SharedPreferences sharedPreferences;
    Boolean session = false;
    public String usernameshared;
    public static final String my_shared_preferences_admin = "my_shared_preferences_admin";
    public static final String session_status = "session_status";
    public final static String TAG_USERNAME = "username";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginadmin);

        context = LoginAdminActivity.this;
        editTextUsernameAdmin = findViewById(R.id.editTextUsernameAdmin);
        editTextPasswordAdmin = findViewById(R.id.hide_show_edittext_password_Admin);
        buttonLoginAdmin = findViewById(R.id.buttonLoginAdmin);
        textInputLayout1 = findViewById(R.id.wrapper1Admin);
        textInputLayout2 = findViewById(R.id.wrapper2Admin);
        pDialog = new ProgressDialog(context);

        Toolbar toolbar = findViewById(R.id.toolbarLoginAdmin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Admin");

        sharedPreferences = getSharedPreferences(my_shared_preferences_admin,Context.MODE_PRIVATE);
        session = sharedPreferences.getBoolean(session_status, false);
        usernameshared = sharedPreferences.getString(TAG_USERNAME, null);
        buttonLoginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        final String username = editTextUsernameAdmin.getText().toString().trim();
        final String password = editTextPasswordAdmin.getText().toString().trim();
        usernameshared = username;
        if (username.isEmpty()){
            editTextUsernameAdmin.setError("username harus di isi");
            editTextUsernameAdmin.setFocusable(true);
        }
        if (password.isEmpty()){
            editTextPasswordAdmin.setError("password harus diisi");
            editTextPasswordAdmin.setFocusable(true);
        }
        else {
            pDialog.setMessage("Login Process ...");
            ShowDialog();

            StringRequest stringRequest = new StringRequest(Request.Method.POST,Config.LOGIN_ADMIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e(TAG,"hasil response : " +response);
                            if (response.contains(Config.LOGIN_SUCCESS_ADMIN)){
                                hideDialog();
                                gotoActivity();
                            }else {
                                Toast.makeText(getApplicationContext(), "Username atau Password salah", Toast.LENGTH_SHORT).show();
                                hideDialog();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideDialog();
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put(Config.KEY_USERNAME_ADMIN,username);
                    params.put(Config.KEY_PASSWORD_ADMIN,password);
                    return params;
                }
            };

            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    private void ShowDialog() {
        if(!pDialog.isShowing()) {
            pDialog.setProgressStyle(pDialog.STYLE_SPINNER);
            pDialog.setProgress(0);
            pDialog.setCancelable(false);
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.setMax(50);
            pDialog.show();
        }
    }

    private void gotoActivity() {
        startActivity(new Intent(LoginAdminActivity.this,UploadBeritaActivity.class));
        finish();
    }

    private void hideDialog() {
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }

    }
}
