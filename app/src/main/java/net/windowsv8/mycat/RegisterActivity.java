package net.windowsv8.mycat;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Windowsv8 on 23/08/2017.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextNama,editTextUsername,editTextPassword,editTextEmail;
    private TextInputLayout textInputLayout1, textInputLayout2, textInputLayout3, textInputLayout4;
    private AppCompatButton buttonTambah;
    int panjangpassword;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        disableAutoFill();

        editTextNama = findViewById(R.id.editTextName);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonTambah = findViewById(R.id.buttonTambah);
        textInputLayout1 = findViewById(R.id.wrapper1Regeister);
        textInputLayout2 = findViewById(R.id.wrapper2Regeister);
        textInputLayout3 = findViewById(R.id.wrapper3Regeister);
        textInputLayout4 = findViewById(R.id.wrapper4Regeister);
        panjangpassword = editTextPassword.getText().length();

        final Toolbar toolbar = findViewById(R.id.toolbarRegisterActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pendafataran Akun");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (panjang() <= 7){
                    textInputLayout3.setError("Silahkan Masukkan Password minimal 8 karakter");
                    buttonTambah.setClickable(false);
                }else {
                    buttonTambah.setClickable(true);
                    textInputLayout3.setErrorEnabled(false);
                }
            }
        });

        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!validasiemail(editTextEmail.getText().toString())){
                    textInputLayout4.setError("Invalid Email");
                    buttonTambah.setClickable(false);
                }else {
                    textInputLayout4.setErrorEnabled(false);
                    buttonTambah.setClickable(true);
                }
            }
        });

        buttonTambah.setOnClickListener(this);

    }

    @TargetApi(Build.VERSION_CODES.O)
    private void disableAutoFill() {
        getWindow().getDecorView().setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS);
    }

    private boolean validasiemail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private int panjang(){
        return editTextPassword.getText().toString().length();
    }

    public void userRegister(){
        final String nama = editTextNama.getText().toString().trim();
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();

        if (TextUtils.isEmpty(nama)){
            textInputLayout1.setError("Silahkan Masukkan Nama Lengkap");
        }
        if (TextUtils.isEmpty(username)){
            textInputLayout2.setError("Silahkan Masukkan username");
        }
        if (TextUtils.isEmpty(password)){
            textInputLayout3.setError("Silahkan Masukkan password dengan minimal 8 karaketer");
        }
        if (TextUtils.isEmpty(email)){
            textInputLayout4.setError("Silahkan Masukkan alamat e-mail");
        }
        else {
            final ProgressDialog dialog = ProgressDialog.show(this,"Mendaftarkan Akun","Tunggu Sebentar...",true, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.URL_ADD,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().equals("Berhasil Mendaftarkan Akun")) {
                                MDToast.makeText(getApplicationContext(), response, MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                                gotoactivity();
                                Log.e("hasil", response);
                                dialog.dismiss();
                            } else {
                                MDToast.makeText(getApplicationContext(), response, MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                                Log.e("hasil", response);
                            }
                            dialog.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterActivity.this, error.toString(),Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams(){
                    Map<String,String> params = new HashMap<>();
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
    }

    public void gotoactivity(){
        editTextNama.setText(null);
        editTextEmail.setText(null);
        editTextUsername.setText(null);
        editTextPassword.setText(null);
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
