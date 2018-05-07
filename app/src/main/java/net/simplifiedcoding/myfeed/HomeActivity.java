package net.simplifiedcoding.myfeed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageView1 = (ImageView)findViewById(R.id.imageHome1);
        imageView2 = (ImageView)findViewById(R.id.imageHome2);
        imageView3 = (ImageView)findViewById(R.id.imageHome3);
        imageView4 = (ImageView)findViewById(R.id.imageHome4);
        textViewUsernameHome = (TextView) findViewById(R.id.usernameHome);

        //sharedpreferences
        user = new User(this);
        txt_id = findViewById(R.id.idHome);
        txt_username = findViewById(R.id.usernameHome1);
        btn_logout = findViewById(R.id.logoutHome);
        final Toolbar toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("MyCat");

        //session Manager sharedpreferences
        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);
        txt_id.setText(id);
        txt_username.setText(username);

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

        textViewUsernameHome.setText(user.getUsername());

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,BeritaActivity.class));
                Toast.makeText(getApplicationContext(), "berhasil2",Toast.LENGTH_SHORT).show();
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "berhasil3",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, DokterActivity.class));
//                startActivity(new Intent(HomeActivity.this,TesDokterActivity.class));
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "berhasil4",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this,AboutHomeActivity.class));
            }
        });
    }

}
