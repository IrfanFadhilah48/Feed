package net.simplifiedcoding.myfeed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
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
    EditText editTextLatUploadCamera,editTextLngUploadCamera;
    ImageView imageViewCameraUpload;
    Bitmap bitmap, decoded, bm;

    int success;
    //private static final String UPLOAD_URL = "http://192.168.8.101/AppProperti/uploadimage2.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private String KEY_PRICE = "price";
    private String KEY_DESCRIPTION= "description";
    private String KEY_LAT= "lat";
    private String KEY_LNG= "lng";
    private User user;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UploadCameraActivity.this,AboutActivity.class));
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
        editTextLatUploadCamera = (EditText)findViewById(R.id.editTextLatUploadCamera);
        editTextLngUploadCamera = (EditText)findViewById(R.id.editTextLngUploadCamera);

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

        user = new User(this);
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte [] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//        byte [] decodeBytes = Base64.decode(encodedImage,Base64.DEFAULT);
//        bm = BitmapFactory.decodeByteArray(decodeBytes,0,decodeBytes.length);
        return encodedImage;
//        return getStringImage(bmp);
    }

    private void uploadImage() {
        final String nama = editTextNameUploadCamera.getText().toString().trim();
        final String harga = editTextPriceUploadCamera.getText().toString().trim();
        final String deskripsi = editTextDescriptionUploadCamera.getText().toString().trim();
        final String lat = editTextLatUploadCamera.getText().toString().trim();
        final String lng = editTextLngUploadCamera.getText().toString().trim();


        if (TextUtils.isEmpty(nama)) {
           editTextNameUploadCamera.setError("Masukkan Nama");
        } else if (TextUtils.isEmpty(harga)) {
            editTextPriceUploadCamera.setError("Masukkan Harga");
        } else if (TextUtils.isEmpty(deskripsi)) {
            editTextDescriptionUploadCamera.setError("Masukkan Deskripsi");
        } else if (TextUtils.isEmpty(lat)) {
            editTextLatUploadCamera.setError("Masukkan Latitude");
        } else if (TextUtils.isEmpty(lng)) {
            editTextLngUploadCamera.setError("Masukkan Longitude");
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
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String>();
                    params.put(KEY_IMAGE, getStringImage(bitmap));
                    params.put(KEY_NAME, nama);
                    params.put(KEY_PRICE, harga);
                    params.put(KEY_DESCRIPTION, deskripsi);
                    params.put(KEY_LAT, lat);
                    params.put(KEY_LNG, lng);
                    params.put(KEY_USERNAME, user.getUsername());
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
        editTextLatUploadCamera.setText(null);
        editTextLngUploadCamera.setText(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE && resultCode == RESULT_OK){

            bitmap = (Bitmap) data.getExtras().get("data");
            imageViewCameraUpload.setImageBitmap(bitmap);

        }
    }
}
