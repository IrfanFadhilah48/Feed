package net.windowsv8.mycat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Windowsv8 on 15/09/2017.
 */

public class UploadActivity extends AppCompatActivity {
    Context context = UploadActivity.this;
    Button buttonChoose;
    FloatingActionButton buttonUpload;
    ImageView imageViewUpload;
    EditText editTextNameUpload, editTextPriceUpload, editTextDescriptionUpload;
    EditText editTextNomorUpload, editTextAlamatUpload, editTextJenisUpload;
    TextInputLayout textInputLayout1, textInputLayout2, textInputLayout3;
    TextInputLayout textInputLayout4, textInputLayout5, textInputLayout6;
    Bitmap bitmap, decoded;
    int success;
    int PICK_IMAGE_REQUEST = 1;
    public static final int TAKE_CAMERA = 0;
    int bitmap_size = 80;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private String KEY_PRICE = "price";
    private String KEY_DESCRIPTION= "description";
    private String KEY_TELEPHONE= "telephone";
    private String KEY_ADDRESS= "address";
    private String KEY_USERNAME = "username";
    private String KEY_JENIS = "jenis";
    SharedPreferences sharedPreferences;
    public final static String TAG_ID = "id";
    String id, pictureImagePath;
    Uri photoURI;

    private static final String TAG = UploadActivity.class.getSimpleName();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UploadActivity.this,MainActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
        editTextJenisUpload = findViewById(R.id.editTextJenisUpload);
        imageViewUpload = (ImageView)findViewById(R.id.imageViewUpload);
        textInputLayout1 = findViewById(R.id.wrapper1UploadActivity);
        textInputLayout2 = findViewById(R.id.wrapper2UploadActivity);
        textInputLayout3 = findViewById(R.id.wrapper3UploadActivity);
        textInputLayout4 = findViewById(R.id.wrapper4UploadActivity);
        textInputLayout5 = findViewById(R.id.wrapper5UploadActivity);
        textInputLayout6 = findViewById(R.id.wrapper6UploadActivity);

        editTextNameUpload.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (panjang() > 40){
                    textInputLayout1.setError("Maaf Nama tidak boleh lebih panjang dari 40 karakter");
//                    buttonUpload.setClickable(false);
                }else {
                    textInputLayout1.setErrorEnabled(false);
                    buttonUpload.setClickable(true);
                }
            }
        });

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
                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);
                    editTextPriceUpload.setText(formattedString);
                    editTextPriceUpload.setSelection(editTextPriceUpload.getText().length());

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
        id = sharedPreferences.getString(TAG_ID,"id");

        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedImage();
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

    }

    private int panjang() {
        return editTextNameUpload.getText().toString().length();
    }

    private void selectedImage() {
            final CharSequence [] items = {"Kamera", "Galeri"};

            AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
            builder.setTitle("Silahkan Pilih");
            builder.setCancelable(true);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (items[i].equals("Kamera")){
                        File directory = new File(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES), "MyCat");

                        if (!directory.exists()) {
                            if (!directory.mkdirs()) {
                                Log.e(TAG, "Failed to create storage directory.");
                            }
                        }

                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String imageFileName = "MyFoto_" + timeStamp + ".jpg";

                        pictureImagePath = directory.getAbsolutePath() + "/" + imageFileName;

                        File file = new File(pictureImagePath);
                        photoURI = FileProvider.getUriForFile(UploadActivity.this,
                                BuildConfig.APPLICATION_ID + ".provider",
                                file);

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(intent, TAKE_CAMERA);

                    }else if(items[i].equals("Galeri")){
                        showFileChooser();
                    }
                }
            });
            builder.show();
    }

    public String getStringImage(Bitmap bmp){
        String encodedImage = "";
        if (imageViewUpload.getDrawable() == null){

        }
        else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
            byte [] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }
        return encodedImage;
    }

    private void uploadImage(){
        final String nama = editTextNameUpload.getText().toString().trim();
        final String harga = editTextPriceUpload.getText().toString().trim().replaceAll(",","");
        final String deskripsi = editTextDescriptionUpload.getText().toString().trim();
        final String nomor = editTextNomorUpload.getText().toString().trim();
        final String alamat = editTextAlamatUpload.getText().toString().trim();
        final String jenis = editTextJenisUpload.getText().toString().trim();

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
        }
        if (TextUtils.isEmpty(jenis)){
            textInputLayout6.setError("Silahkan Masukkan Jenis Kucing");
        }
        else {
            final ProgressDialog loading = ProgressDialog.show(this, "Uploading....", "Please Wait...", false, false);
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
                                    MDToast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                                    kosong();
                                    startActivity(new Intent(UploadActivity.this, MainActivity.class));
                                } else {
                                    MDToast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING).show();
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
                            Toast.makeText(UploadActivity.this, "maaf file foto terlalu besar", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
//                    params.put(KEY_IMAGE, getStringImage(decoded));
                    params.put(KEY_IMAGE, getStringImage(bitmap));
                    params.put(KEY_NAME, nama);
                    params.put(KEY_PRICE, harga);
                    params.put(KEY_DESCRIPTION, deskripsi);
                    params.put(KEY_TELEPHONE, nomor);
                    params.put(KEY_ADDRESS, alamat);
                    params.put(KEY_JENIS, jenis);
                    params.put(KEY_USERNAME, sharedPreferences.getString(TAG_ID, "id"));
                    Log.d(TAG, "upload" + params);
                    return params;
                }
            };
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_CAMERA){
            if (resultCode == Activity.RESULT_OK){
                File imgFile = new File(pictureImagePath);
                Glide.with(context)
                        .asBitmap()
                        .load(imgFile)
                        .into(imageViewUpload);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                    setToImageView(getResizedBitmap(bitmap, 1024));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else {
                Toast.makeText(getApplicationContext(),"Tidak Bisa mengambil Gambar", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == PICK_IMAGE_REQUEST){
            if (resultCode == Activity.RESULT_OK){
                Uri filepath = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                    setToImageView(getResizedBitmap(bitmap, 1024));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
//            Uri filepath = data.getData();
//            try{
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
//                setToImageView(getResizedBitmap(bitmap,1024));
//                //nilai 512
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
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
