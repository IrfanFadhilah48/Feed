package net.windowsv8.mycat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Windowsv8 on 21/06/2018.
 */

public class SplashScreenActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        imageView = findViewById(R.id.iv_splash);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_splash);
        imageView.startAnimation(animation);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, SplashScreen.class));
            }
        },3000);
        getWindow().setStatusBarColor(getResources().getColor(R.color.birumuda));
    }
}
