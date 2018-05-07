package net.simplifiedcoding.myfeed;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Windowsv8 on 08/11/2017.
 */

public class AboutHomeActivity extends AppCompatActivity {
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_about);

        text = findViewById(R.id.scrollText);
        text.setSelected(true);
        getSupportActionBar().setTitle("About");
    }
}
