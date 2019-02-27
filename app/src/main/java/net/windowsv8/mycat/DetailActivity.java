package net.windowsv8.mycat;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Objects;


/**
 * Created by Windowsv8 on 24/08/2017.
 */

public class DetailActivity extends AppCompatActivity {

    String numberTelephone = "";
    String AddressAlamat = "";
    ImageView imageViewCollapse;
    TextView textViewNameDetail, textViewPublisherDetail, textViewDeskripsiDetail, textViewTelephoneDetail, textViewAddressDetail;
    Context context2;
    Button terjual;
    String cekuser, title, message = "Apakah hewannya masih ada?";
    BottomNavigationView bottomNavigationView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    SharedPreferences sharedPreferences;
    public static final String TAG_ID = "id";
    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fjbdetail);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarFJB);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Bundle extraimage = getIntent().getExtras();
        String image = extraimage.getString("image");

        imageViewCollapse = findViewById(R.id.imageViewCollapse);
        Picasso.with(context2)
                .load(image)
                .error(R.drawable.image)
                .into(imageViewCollapse);

        Bundle extraname = getIntent().getExtras();
        final String name = extraname.getString("name");
        textViewNameDetail = findViewById(R.id.textViewNameDetail);
        textViewNameDetail.setText(name);

        title = name;

        Bundle extraprice = getIntent().getExtras();
        String price = extraprice.getString("price");

        Bundle namauser = getIntent().getExtras();
        String hasilnamauser = Objects.requireNonNull(namauser).getString("nama");
        if (hasilnamauser != null){
            Log.e("namauser", hasilnamauser);
        }else {
            Toast.makeText(getApplicationContext(), "kosong", Toast.LENGTH_SHORT).show();
        }

        Bundle email = getIntent().getExtras();
        String emailuser = email.getString("email");
        if (emailuser != null){
            Log.e("namauser", emailuser);
        }else {
            Toast.makeText(getApplicationContext(), "kosong", Toast.LENGTH_SHORT).show();
        }


        //idr
        DecimalFormat format = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols idr = new DecimalFormatSymbols();
        idr.setCurrencySymbol("Rp ");
        idr.setGroupingSeparator('.');
        format.setDecimalFormatSymbols(idr);
        textViewPublisherDetail = findViewById(R.id.textViewPublisherDetail);
        textViewPublisherDetail.setText(format.format(Long.valueOf(price)));

        Bundle extradescription = getIntent().getExtras();
        String description = extradescription.getString("description");
        textViewDeskripsiDetail = findViewById(R.id.textviewDeskripsiDetail);
        textViewDeskripsiDetail.setText(description);

        Bundle extratelephone = getIntent().getExtras();
        final String telephone = extratelephone.getString("telephone");
        textViewTelephoneDetail = findViewById(R.id.textviewTeleponDetail);
        textViewTelephoneDetail.setText(telephone);

        Bundle extraaddress = getIntent().getExtras();
        final String address = extraaddress.getString("address");
        textViewAddressDetail = findViewById(R.id.textviewAlamatDetail);
        textViewAddressDetail.setText(address);

        Bundle extraid = getIntent().getExtras();
        final String ids = extraid.getString("id");
        Log.e("apakahdapat", ids);


        Bundle extrausername = getIntent().getExtras();
        String username = extrausername.getString("username");
        sharedPreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        cekuser = sharedPreferences.getString(TAG_ID,"id");
        terjual = findViewById(R.id.terjual);
        if (username.equals(cekuser)){
            terjual.setVisibility(View.VISIBLE);
            terjual.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ShowToast")
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"apakah berhasil",Toast.LENGTH_SHORT);
                    hapusPenjualan();
                }
            });
        }


        bottomNavigationView = findViewById(R.id.bottomnavigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        collapsingToolbarLayout.setTitle(title);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#ffffffff"));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.TextSize);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.telepon:
                        numberTelephone = telephone.trim();
                        call();
                        break;

                    case R.id.sms:
                        numberTelephone = telephone.trim();
                        sms();
                        break;

                    case R.id.maps1:
                        AddressAlamat = address.trim();
                        maps();
                        break;

                    case R.id.whatsapp:
                        whatsApp(telephone,name);
                        Toast.makeText(getApplicationContext(),"Berhasil",Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

     }

    private void hapusPenjualan() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.DELETE_DATA_URL + Objects.requireNonNull(getIntent().getExtras()).getString("id"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response);
                            int success = json.getInt("success");
                            if (success == 1){
                                Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DetailActivity.this, MainActivity.class));
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void sold() {

    }

    private void sms() {
        Intent intentSms = new Intent(Intent.ACTION_VIEW);
        intentSms.setData(Uri.parse("sms:"+numberTelephone));
        intentSms.putExtra("sms_body",message);
        startActivity(intentSms);
    }

    private void call() {
        Intent intentCall = new Intent(Intent.ACTION_DIAL);
        intentCall.setData(Uri.parse("tel:"+numberTelephone));
        startActivity(intentCall);
    }

    private void maps(){
        Uri addressUri = Uri.parse("geo:0,0?q=" + AddressAlamat);
        Intent intentAddress = new Intent(Intent.ACTION_VIEW, addressUri);
        intentAddress.setPackage("com.google.android.apps.maps");
        startActivity(intentAddress);
    }

    public void whatsApp(String number, String barang){
        StringBuilder nomorWA = new StringBuilder(number);
        nomorWA.setCharAt(0,'6');
        nomorWA.insert(1,'2');
        Intent sendMessage = new Intent("android.intent.action.MAIN");
        sendMessage.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
        sendMessage.setType("text/plain");
        sendMessage.putExtra("jid", nomorWA + "@s.whatsapp.net");
        sendMessage.putExtra(Intent.EXTRA_TEXT,"Apakah hewan ini masih tersedia " + barang);
        startActivity(sendMessage);
    }

}
