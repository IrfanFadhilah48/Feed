package net.simplifiedcoding.myfeed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.load.model.MediaStoreFileLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.internal.zzoe.ko;

/**
 * Created by Windowsv8 on 15/09/2017.
 */

public class UploadActivity extends AppCompatActivity {
    Button buttonChoose;
    FloatingActionButton buttonUpload;
    ImageView imageViewUpload;
    EditText editTextNameUpload, editTextPriceUpload, editTextDescriptionUpload, editTextLatUpload, editTextLngUpload;
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
    private String KEY_LAT= "lat";
    private String KEY_LNG= "lng";
    private String KEY_USERNAME = "username";
    private User user;

    String tag_json_obj = "json_obj_req";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UploadActivity.this,AboutActivity.class));
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
        editTextLatUpload = (EditText)findViewById(R.id.editTextLatUpload);
        editTextLngUpload = (EditText)findViewById(R.id.editTextLngUpload);

        imageViewUpload = (ImageView)findViewById(R.id.imageViewUpload);

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
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading....","Please Wait...",false,false);
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
                                startActivity(new Intent(UploadActivity.this,MainActivity.class));
                            } else {
                                Toast.makeText(UploadActivity.this, jObj.getString(TAG_SUCCESS), Toast.LENGTH_LONG).show();
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

                    Toast.makeText(UploadActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    Log.e(TAG,error.getMessage().toString());
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_IMAGE,getStringImage(decoded));
                params.put(KEY_NAME,editTextNameUpload.getText().toString().trim());
                params.put(KEY_PRICE,editTextPriceUpload.getText().toString().trim());
                params.put(KEY_DESCRIPTION,editTextDescriptionUpload.getText().toString().trim());
                params.put(KEY_LAT,editTextLatUpload.getText().toString().trim());
                params.put(KEY_LNG,editTextLngUpload.getText().toString().trim());
                params.put(KEY_USERNAME, user.getUsername());
                Log.e(TAG,"" + params);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest,tag_json_obj);
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
        editTextLatUpload.setText(null);
        editTextLngUpload.setText(null);
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
