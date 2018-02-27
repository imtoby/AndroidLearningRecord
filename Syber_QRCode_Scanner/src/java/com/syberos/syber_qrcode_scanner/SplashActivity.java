package com.syberos.syber_qrcode_scanner;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {

    private static final String TAG = "SplashActivity";

    final private static int PERMISSIONS_CODE = 29; // 请求码

    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET,
    };

    private PermissionsChecker permissionsChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        permissionsChecker = new PermissionsChecker(this);
    }

    @Override
    protected void onResume() {
        if (permissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        } else {
            showMainActivity();
            finish();
        }
        super.onResume();
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, PERMISSIONS_CODE, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == PERMISSIONS_CODE &&
                resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
        } else {
            showMainActivity();
        }
        finish();
    }

    private void showMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
