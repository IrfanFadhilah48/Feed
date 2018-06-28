package net.simplifiedcoding.myfeed;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static net.simplifiedcoding.myfeed.LoginActivity.MyPREFERENCES;
import static net.simplifiedcoding.myfeed.LoginActivity.USERNAME;

/**
 * Created by Windowsv8 on 08/11/2017.
 */

public class HomeActivity extends AppCompatActivity{
    private ImageView imageView1,imageView2,imageView3,imageView4;
    private TextView textViewUsernameHome;
    Context context;
    private User user;

    Button btn_logout;
    TextView txt_id, txt_username;
    String id, username;
    SharedPreferences sharedpreferences;
    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    private static final int keluar = 2000;
    private long mbackpressed;
    public static final String shared_pref = "my_shared_preferences";

    @Override
    public void onBackPressed() {
        if (mbackpressed + keluar > System.currentTimeMillis()){
//            digunakan untuk keluar dari activity dan menghapus service
//            finishAndRemoveTask();
            finishAffinity();
            super.onBackPressed();
            return;
        }
        else {
            Toast.makeText(this,"tekan kembali untuk keluar", Toast.LENGTH_SHORT).show();
        }
        mbackpressed = System.currentTimeMillis();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageView1 = (ImageView)findViewById(R.id.imageHome1);
        imageView2 = (ImageView)findViewById(R.id.imageHome2);
        imageView3 = (ImageView)findViewById(R.id.imageHome3);
        imageView4 = (ImageView)findViewById(R.id.imageHome4);
        textViewUsernameHome = (TextView) findViewById(R.id.usernameHome);
        final Toolbar toolbar = findViewById(R.id.toolbarHome);

        //sharedpreferences
        user = new User(this);
        txt_id = findViewById(R.id.idHome);
        txt_username = findViewById(R.id.usernameHome1);
        btn_logout = findViewById(R.id.logoutHome);

        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("MyCat");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.birumuda)));



        //session Manager sharedpreferences
        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        id = sharedpreferences.getString(TAG_ID, "id");
        username = sharedpreferences.getString(TAG_USERNAME, "username");
        txt_id.setText(id);
        txt_username.setText(username);
        Log.e("data", id+"\n"+username);

        //btn_logout.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.logout),null, null, null);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //keluar session manager SharedPreference
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginActivity.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.commit();
//                user.setKEY_ID(user.KEY_ID,null);
//                user.setUsername(user.KEY_USERNAME,null);
//                user.setKEY_LOGIN(user.KEY_LOGIN, false);

                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
            }
        });

//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        String load = sharedPreferences.getString(MyPREFERENCES, );
//       if (!SharedPrefManager.getInstance(this).isLoggedIn()){
//            finish();
//            startActivity(new Intent(this, LoginActivity.class));
//        }
        //memanggil sharedPreference
//        User user = SharedPrefManager.getInstance(this).getUser();

//        textViewUsernameHome.setText(user.getUsername());

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,BeritaActivity.class));
                Toast.makeText(getApplicationContext(), "berhasil2",Toast.LENGTH_SHORT).show();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "berhasil3",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, DokterActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "berhasil4",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this,UploadPilihActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }


}
