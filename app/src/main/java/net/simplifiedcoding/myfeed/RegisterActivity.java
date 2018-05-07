package net.simplifiedcoding.myfeed;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
 * Created by Windowsv8 on 23/08/2017.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextNama,editTextUsername,editTextPassword,editTextEmail;
    private AppCompatButton buttonTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        editTextNama = (EditText) findViewById(R.id.editTextName);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        buttonTambah = (AppCompatButton) findViewById(R.id.buttonTambah);

        buttonTambah.setOnClickListener(this);
    }

    public void userRegister(){
        final String nama = editTextNama.getText().toString().trim();
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.URL_ADD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("Berhasil Register")) {
                            Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                            gotoactivity();
                        } else {
                            Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, error.toString(),Toast.LENGTH_LONG).show();
                    }
                })
            {
                @Override
                protected Map<String, String> getParams(){
                    Map<String,String> params = new HashMap<String,String>();
                    params.put(Config.KEY_EMP_NAMA,nama);
                    params.put(Config.KEY_EMP_USERNAME,username);
                    params.put(Config.KEY_EMP_PASSWORD,password);
                    params.put(Config.KEY_EMP_EMAIL,email);
                    return params;
                }
            };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void gotoactivity(){
        editTextNama.setText("");
        editTextEmail.setText("");
        editTextUsername.setText("");
        editTextPassword.setText("");
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view == buttonTambah){
            userRegister();
        }
    }
}
