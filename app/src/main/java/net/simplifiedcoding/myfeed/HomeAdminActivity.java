package net.simplifiedcoding.myfeed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Windowsv8 on 06/05/2018.
 */

public class HomeAdminActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView imageView1,imageView2,imageView3,imageView4;
    private TextView textViewUsernameHome;
    Context context;

    Button btn_logout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        imageView1 = findViewById(R.id.imageHome1Admin);
        imageView2 = findViewById(R.id.imageHome2Admin);
        imageView3 = findViewById(R.id.imageHome3Admin);
        imageView4 = findViewById(R.id.imageHome4Admin);
        btn_logout = findViewById(R.id.logoutHomeAdmin);

        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbarHomeAdmin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Event");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageHome1Admin:
                startActivity(new Intent(HomeAdminActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.imageHome2Admin:
                startActivity(new Intent(HomeAdminActivity.this, BeritaAdminActivity.class));
                finish();
                break;

            case R.id.imageHome3Admin:
                startActivity(new Intent(HomeAdminActivity.this, DokterActivity.class));
                finish();
                break;

            case R.id.imageHome4Admin:
                startActivity(new Intent(HomeAdminActivity.this, AboutHomeActivity.class));
                finish();
                break;
        }
    }
}
