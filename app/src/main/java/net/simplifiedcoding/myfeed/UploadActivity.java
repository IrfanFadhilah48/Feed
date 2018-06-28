package net.simplifiedcoding.myfeed;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.load.model.MediaStoreFileLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.google.android.gms.internal.zzoe.ko;

/**
 * Created by Windowsv8 on 15/09/2017.
 */

public class UploadActivity extends AppCompatActivity {
    Button buttonChoose;
    FloatingActionButton buttonUpload;
    ImageView imageViewUpload;
    EditText editTextNameUpload, editTextPriceUpload, editTextDescriptionUpload, editTextNomorUpload, editTextAlamatUpload;
    TextInputLayout textInputLayout1, textInputLayout2, textInputLayout3, textInputLayout4, textInputLayout5;
    Bitmap bitmap, decoded;
    int success;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60;
    Double Lat,Lng;
    String locationLat,locaionLng;

    private static final String TAG = UploadActivity.class.getSimpleName();

    //private static final String UPLOAD_URL = "http://192.168.8.101/AppProperti/uploadimage2.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private String KEY_PRICE = "price";
    private String KEY_DESCRIPTION= "description";
    private String KEY_TELEPHONE= "telephone";
    private String KEY_ADDRESS= "address";
//    private String KEY_LAT = "lat";
//    private String KEY_LNG = "lng";
    private String KEY_USERNAME = "username";
    SharedPreferences sharedPreferences;
    public final static String TAG_ID = "id";
    public final static String TAG_USERNAME = "username";
    private User user;
    String username,id;

    String tag_json_obj = "json_obj_req";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UploadActivity.this,UploadPilihActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        buttonChoose = (Button)findViewById(R.id.buttonChooseUpload);
        buttonUpload = (FloatingActionButton)findViewById(R.id.buttonUpload);
        editTextNameUpload = (EditText)findViewById(R.id.editTextNameUpload);
        editTextPriceUpload = (EditText)findViewById(R.id.editTextPriceUpload);
        editTextDescriptionUpload = (EditText)findViewById(R.id.editTextDescriptionUpload);
        editTextNomorUpload = (EditText)findViewById(R.id.editTextNomorUpload);
        editTextAlamatUpload = (EditText)findViewById(R.id.editTextAlamatUpload);
        imageViewUpload = (ImageView)findViewById(R.id.imageViewUpload);
        textInputLayout1 = findViewById(R.id.wrapper1UploadActivity);
        textInputLayout2 = findViewById(R.id.wrapper2UploadActivity);
        textInputLayout3 = findViewById(R.id.wrapper3UploadActivity);
        textInputLayout4 = findViewById(R.id.wrapper4UploadActivity);
        textInputLayout5 = findViewById(R.id.wrapper5UploadActivity);

        editTextPriceUpload.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editTextPriceUpload.removeTextChangedListener(this);

                try {
                    String givenstring = s.toString();
                    Long longval;
                    if (givenstring.contains(",")) {
                        givenstring = givenstring.replaceAll(",", "");
                    }
                    longval = Long.parseLong(givenstring);
//                    DecimalFormat formatter = new DecimalFormat("#,###,###,###");
                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);
                    editTextPriceUpload.setText(formattedString);
                    editTextPriceUpload.setSelection(editTextPriceUpload.getText().length());
                    // to place the cursor at the end of text
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                editTextPriceUpload.addTextChangedListener(this);
            }
        });

        final Toolbar toolbar = findViewById(R.id.toolbarUploadActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Upload Hewan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        sharedPreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
//        username = sharedPreferences.getString(TAG_USERNAME,"username");
//        Log.d(TAG,"username" + username);
        id = sharedPreferences.getString(TAG_ID,"id");
        Log.d(TAG,"id input :" +id);

        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        user = new User(this);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte [] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        final String nama = editTextNameUpload.getText().toString().trim();
        final String harga = editTextPriceUpload.getText().toString().trim().replaceAll(",","");
        final String deskripsi = editTextDescriptionUpload.getText().toString().trim();
        final String nomor = editTextNomorUpload.getText().toString().trim();
        final String alamat = editTextAlamatUpload.getText().toString().trim();

        if (TextUtils.isEmpty(nama)) {
            textInputLayout1.setError("Silahkan Masukkan Nama");
        }if (TextUtils.isEmpty(harga)) {
            textInputLayout2.setError("Silahkan Masukkan Harga");
        }if (TextUtils.isEmpty(deskripsi)) {
            textInputLayout3.setError("Silahkan Masukkan Deskripsi Hewan yang Lengkap");
        }if (TextUtils.isEmpty(nomor)) {
            textInputLayout4.setError("Silahkan Masukkan Nomor Handphone");
        }if (TextUtils.isEmpty(alamat)) {
            textInputLayout5.setError("Silahkan Masukkan Alamat Lengkap");
        } else {
            final ProgressDialog loading = ProgressDialog.show(this, "Uploading....", "PlPease Wait...", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.UPLOAD_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e(TAG, "Response:" + response.toString());
                            try {
                                JSONObject jObj = new JSONObject(response);
                                success = jObj.getInt(TAG_SUCCESS);

                                if (success == 1) {
                                    Log.e("v add", jObj.toString());
                                    Toast.makeText(UploadActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                    kosong();
                                    startActivity(new Intent(UploadActivity.this, MainActivity.class));
                                } else {
                                    Toast.makeText(UploadActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            loading.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.dismiss();

                            Toast.makeText(UploadActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                            Log.e(TAG, error.getMessage().toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_IMAGE, getStringImage(decoded));
                    params.put(KEY_NAME, nama);
                    params.put(KEY_PRICE, harga);
                    params.put(KEY_DESCRIPTION, deskripsi);
                    params.put(KEY_TELEPHONE, nomor);
                    params.put(KEY_ADDRESS, alamat);
                    params.put(KEY_USERNAME, sharedPreferences.getString(TAG_ID, "id"));
                    Log.d(TAG, "upload" + params);

                    return params;
                }
            };
//        AppController.getInstance().addToRequestQueue(stringRequest,tag_json_obj);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE_REQUEST);
    }

    //berfungsi menampilkan image yang dipilih dari galeri ke dalam image view di activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri filepath = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                setToImageView(getResizedBitmap(bitmap,1024));
                //nilai 512
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void kosong(){
        imageViewUpload.setImageResource(0);
        editTextNameUpload.setText(null);
        editTextPriceUpload.setText(null);
        editTextDescriptionUpload.setText(null);
        editTextNomorUpload.setText(null);
        editTextAlamatUpload.setText(null);
    }

    private void setToImageView(Bitmap bmp){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        imageViewUpload.setImageBitmap(decoded);
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize){
        int width = image.getWidth();
        int height = image.getHeight();

        float bitMapRatio = (float)width/(float)height;
        if (bitMapRatio > 1){
            width = maxSize;
            height = (int) (width / bitMapRatio);
        }else {
            height = maxSize;
            width = (int)(height * bitMapRatio);
        }
        return Bitmap.createScaledBitmap(image, width,height,true);
    }
}
