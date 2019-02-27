package net.windowsv8.mycat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;


/**
 * Created by Windowsv8 on 08/11/2017.
 */

public class HomeActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener{
    private ImageView imageView1,imageView2,imageView3,imageView4;
    Context context = this;

    CardView cardView1, cardView2, cardView3, cardView4;
    String id, username;
    SliderLayout mDemoSlider;
    SharedPreferences sharedpreferences;
    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";
    private static final int keluar = 2000;
    private long mbackpressed;

    @Override
    public void onBackPressed() {
        if (mbackpressed + keluar > System.currentTimeMillis()){
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
        cardView1 = findViewById(R.id.cardHomeFJB);
        cardView2 = findViewById(R.id.cardHomeEvent);
        cardView3 = findViewById(R.id.cardHomeDokter);
        cardView4 = findViewById(R.id.cardHomeAbout);
        mDemoSlider = findViewById(R.id.slider);

        final Toolbar toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("MyCat");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.birumuda)));

        //session Manager sharedpreferences
        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        id = sharedpreferences.getString(TAG_ID, "id");
        username = sharedpreferences.getString(TAG_USERNAME, "username");
        Log.e("data", id+"\n"+username);
        imageSlider();

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,BeritaActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, PetshopActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,AboutHomeActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
    }

    private void imageSlider() {
        HashMap<String, Integer> url = new HashMap<String, Integer>();

        url.put(" ", R.drawable.kucing);
        url.put("  ", R.drawable.kucing2);
        url.put("   ", R.drawable.kucing3);

        for (String name : url.keySet()) {
            TextSliderView textSliderView = new TextSliderView(HomeActivity.this);
            textSliderView.description(name)
                    .image(url.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
    }

//        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
//        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setBackgroundColor(getResources().getColor(R.color.birumuda));
        mDemoSlider.setCustomIndicator((PagerIndicator)findViewById(R.id.custom_indicator));
        mDemoSlider.setDuration(3000);
        mDemoSlider.addOnPageChangeListener(HomeActivity.this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.keluarUser:
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginActivity.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.commit();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
