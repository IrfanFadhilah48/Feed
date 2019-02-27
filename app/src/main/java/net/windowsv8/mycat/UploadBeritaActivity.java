package net.windowsv8.mycat;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Windowsv8 on 06/05/2018.
 */

public class UploadBeritaActivity extends AppCompatActivity {

    EditText editTextJudulEvent, editTextIsiEvent, editTextTanggalEvent, editTextTempatEvent, editTextContactEvent;
    Button buttonUploadBerita;
    Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private static final String TAG = UploadBeritaActivity.class.getSimpleName();

    String convert;
    int success;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    TextInputLayout textInputLayout1, textInputLayout2, textInputLayout3, textInputLayout4, textInputLayout5;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,LoginAdminActivity.class));
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_berita);

        editTextJudulEvent = findViewById(R.id.editTextUploadJudul);
        editTextIsiEvent = findViewById(R.id.editTextUploadIsi);
        editTextTanggalEvent = findViewById(R.id.editTextUploadTanggal);
        editTextTempatEvent = findViewById(R.id.editTextUploadTempat);
        editTextContactEvent = findViewById(R.id.editTextUploadNomor);
        textInputLayout1 = findViewById(R.id.wrapper1UploadAdmin);
        textInputLayout2 = findViewById(R.id.wrapper2UploadAdmin);
        textInputLayout3 = findViewById(R.id.wrapper3UploadAdmin);
        textInputLayout4 = findViewById(R.id.wrapper4UploadAdmin);
        textInputLayout5 = findViewById(R.id.wrapper5UploadAdmin);
        buttonUploadBerita = findViewById(R.id.buttonUploadBerita);

        Toolbar toolbar = findViewById(R.id.toolbarBeritaUploadAdmin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Event");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        calendar = Calendar.getInstance();
        editTextTanggalEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate();
            }
        });

        buttonUploadBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "judul" + editTextJudulEvent.getText().toString());
                Log.e(TAG, "isi" + editTextIsiEvent.getText().toString());
                Log.e(TAG, "Dapet tanggal" + editTextTanggalEvent.getText().toString());
                Log.e(TAG, "Tempat" + editTextTempatEvent.getText().toString());
                Log.e(TAG, "Contact" + editTextContactEvent.getText().toString());
                uploadEvent();
            }
        });
    }

    private void showDate() {
        datePickerDialog = new DatePickerDialog(UploadBeritaActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, date);

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date now  = Calendar.getInstance().getTime();
                sdf.format(now);
                if (calendar.getTime().equals(now) || calendar.getTime().after(now)){
                    editTextTanggalEvent.setText(sdf.format(calendar.getTime()));
                    convert = sdf.format(calendar.getTime());
                }
                else {
                    MDToast.makeText(getApplicationContext(), "Tanggal yang dimasukkan harus tanggal yang belum terlewati", MDToast.LENGTH_LONG, MDToast.TYPE_WARNING).show();
//                    Toast.makeText(getApplicationContext(), "Tanggal yang dimasukkan harus tanggal yang belum terlewati", Toast.LENGTH_SHORT).show();
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    private void uploadEvent() {
        final String judul = editTextJudulEvent.getText().toString();
        final String isi = editTextIsiEvent.getText().toString();
        final String tanggal = convert;
        final String tempat = editTextTempatEvent.getText().toString();
        final String nomor = editTextContactEvent.getText().toString();

        if (judul.isEmpty()){
            textInputLayout1.setError("Silahkan Masukkan Judul Event");
        }
        if (isi.isEmpty()){
            textInputLayout2.setError("Silahkan Masukkan Isi Event");
        }
        if (editTextTanggalEvent.getText().toString().isEmpty()){
            textInputLayout3.setError("Silahkan Masukkan Tanggal Event");
        }
        if (tempat.isEmpty()){
            textInputLayout4.setError("Silahkan Masukkan Tempat Eent");
        }
        if (nomor.isEmpty()){
            textInputLayout5.setError("Silahkan Masukkan Contact Person");
        }
        else {
            final ProgressDialog progressDialog = ProgressDialog.show(this,"Uploading","Loading....",false,false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.UPLOAD_BERITA,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jObj = new JSONObject(response);
                                success = jObj.getInt(TAG_SUCCESS);

                                if (success == 1) {
                                    Log.e("tambah : ", jObj.toString());
//                                    Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                                    MDToast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                                    kosong();
                                    gotoActivity();
                                }else {
                                    MDToast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING).show();
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put(Config.KEY_JUDUL, judul);
                    params.put(Config.KEY_ISI, isi);
                    params.put(Config.KEY_TANGGAL, tanggal);
                    params.put(Config.KEY_TEMPAT, tempat);
                    params.put(Config.KEY_CONTACT, nomor);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

    }

    private void gotoActivity() {
        startActivity(new Intent(UploadBeritaActivity.this, UploadBeritaActivity.class));
        finish();
    }


    private void kosong(){
        editTextJudulEvent.setText(null);
        editTextIsiEvent.setText(null);;
        editTextTanggalEvent.setText(null);
        editTextTempatEvent.setText(null);
        editTextContactEvent.setText(null);
        textInputLayout1.setErrorEnabled(false);
        textInputLayout2.setErrorEnabled(false);
        textInputLayout3.setErrorEnabled(false);
        textInputLayout4.setErrorEnabled(false);
        textInputLayout5.setErrorEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_upload_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.keluarAdmin:
                startActivity(new Intent(this, LoginAdminActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
