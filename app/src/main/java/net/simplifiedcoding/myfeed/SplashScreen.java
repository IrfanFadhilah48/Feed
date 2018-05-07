package net.simplifiedcoding.myfeed;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

/**
 * Created by Windowsv8 on 25/04/2018.
 */

public class SplashScreen extends AppCompatActivity {
    private PermissionHelper permissionHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        permissionHelper = new PermissionHelper(this);

        checkAndRequestPermission();
    }

    private boolean checkAndRequestPermission() {

        permissionHelper.permissionListener(new PermissionHelper.PermissionListener() {
            @Override
            public void onPermissionCheckDone() {
                startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                finish();
            }
        });

        permissionHelper.checkAndRequestPermissions();
        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onRequestCallBack(requestCode,permissions,grantResults);
    }
}
