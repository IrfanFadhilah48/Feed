package net.simplifiedcoding.myfeed;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import static android.os.Build.VERSION_CODES.M;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static net.simplifiedcoding.myfeed.Config.KEY_USERNAME;
import static net.simplifiedcoding.myfeed.Config.UPLOAD_URL;

/**
 * Created by Windowsv8 on 22/09/2017.
 */

public class UploadCameraActivity extends AppCompatActivity {


    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    Button buttonCamera;
    FloatingActionButton buttonUploadCamera;
    EditText editTextNameUploadCamera,editTextPriceUploadCamera,editTextDescriptionUploadCamera;
    EditText editTextNomorUploadCamera,editTextAlamatUploadCamera;
    ImageView imageViewCameraUpload;
    Bitmap bitmap, decoded, bm;
    SharedPreferences sharedPreferences;
    TextInputLayout textInputLayout1, textInputLayout2, textInputLayout3, textInputLayout4, textInputLayout5;

    int success;
    //private static final String UPLOAD_URL = "http://192.168.8.101/AppProperti/uploadimage2.php";
    String id, username;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    public final static String TAG_ID = "id";
    public final static String TAG_USERNAME = "username";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private String KEY_PRICE = "price";
    private String KEY_DESCRIPTION= "description";
    private String KEY_TELEPHONE= "telephone";
    private String KEY_ADDRESS= "address";
    private User user;
    private static final String TAG = UploadCameraActivity.class.getSimpleName();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UploadCameraActivity.this,UploadPilihActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_camera);

        buttonCamera = (Button)findViewById(R.id.buttonCamera);
        buttonUploadCamera = (FloatingActionButton)findViewById(R.id.buttonUploadCamera);

        imageViewCameraUpload = (ImageView)findViewById(R.id.imageViewCameraUpload);
        editTextNameUploadCamera = (EditText)findViewById(R.id.editTextNameUploadCamera);
        editTextPriceUploadCamera  = (EditText)findViewById(R.id.editTextPriceUploadCamera);
        editTextDescriptionUploadCamera = (EditText)findViewById(R.id.editTextDescriptionUploadCamera);
        editTextNomorUploadCamera = (EditText)findViewById(R.id.editTextNomorUploadCamera);
        editTextAlamatUploadCamera = (EditText)findViewById(R.id.editTextAlamatUploadCamera);
        textInputLayout1 = findViewById(R.id.wrapper1UploadCameraActivity);
        textInputLayout2 = findViewById(R.id.wrapper2UploadCameraActivity);
        textInputLayout3 = findViewById(R.id.wrapper3UploadCameraActivity);
        textInputLayout4 = findViewById(R.id.wrapper4UploadCameraActivity);
        textInputLayout5 = findViewById(R.id.wrapper5UploadCameraActivity);

        editTextPriceUploadCamera.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editTextPriceUploadCamera.removeTextChangedListener(this);

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
                    editTextPriceUploadCamera.setText(formattedString);
                    editTextPriceUploadCamera.setSelection(editTextPriceUploadCamera.getText().length());
                    // to place the cursor at the end of text
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                editTextPriceUploadCamera.addTextChangedListener(this);
            }
        });

        final Toolbar toolbar = findViewById(R.id.toolbarUploadCameraActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Upload Hewan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }
        });

        buttonUploadCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        sharedPreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        id = sharedPreferences.getString(TAG_ID,"id");
        Log.e(TAG,"dapet ID :" + id);

//        user = new User(this);
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        byte [] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage() {
        final String nama = editTextNameUploadCamera.getText().toString().trim();
        final String harga = editTextPriceUploadCamera.getText().toString().trim().replaceAll(",","");
        final String deskripsi = editTextDescriptionUploadCamera.getText().toString().trim();
        final String nomor = editTextNomorUploadCamera.getText().toString().trim();
        final String alamat = editTextAlamatUploadCamera.getText().toString().trim();


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
            final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please Wait...", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.UPLOAD_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jObj = new JSONObject(response);
                                success = jObj.getInt(TAG_SUCCESS);

                                if (success == 1) {
                                    Log.e("v add", jObj.toString());
                                    Toast.makeText(UploadCameraActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                                    kosong();
                                } else {
                                    Toast.makeText(UploadCameraActivity.this, jObj.getString(TAG_SUCCESS), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(UploadCameraActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT);
                            Log.e(TAG,error.getMessage().toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String>();
                    params.put(KEY_IMAGE, getStringImage(bitmap));
                    params.put(KEY_NAME, nama);
                    params.put(KEY_PRICE, harga);
                    params.put(KEY_DESCRIPTION, deskripsi);
                    params.put(KEY_TELEPHONE, nomor);
                    params.put(KEY_ADDRESS, alamat);
                    params.put(KEY_USERNAME, sharedPreferences.getString(TAG_ID,"id"));
                    Log.e(TAG,"upload" + params);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    private void kosong(){
        imageViewCameraUpload.setImageResource(0);
        editTextNameUploadCamera.setText(null);
        editTextPriceUploadCamera.setText(null);
        editTextDescriptionUploadCamera.setText(null);
        editTextNomorUploadCamera.setText(null);
        editTextAlamatUploadCamera.setText(null);
        imageViewCameraUpload.setFocusable(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE && resultCode == RESULT_OK){

            bitmap = (Bitmap) data.getExtras().get("data");
//            imageViewCameraUpload.setImageBitmap(bitmap);
            setToImageView(getResizedBitmap(bitmap,1024));

        }
    }

    private void setToImageView(Bitmap bmp){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,60, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        imageViewCameraUpload.setImageBitmap(decoded);
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
