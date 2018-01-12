package com.syberos.coolweather.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    final private static int PERMISSIONS_CODE = 29; // 请求码
    final private static int START_ACTIVITY_CODE = 30;

    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.INTERNET
    };

    private PermissionsChecker permissionsChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionsChecker = new PermissionsChecker(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (permissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        } else {
            Intent intent = new Intent(this, ChooseAreaActivity.class);
            startActivityForResult(intent, START_ACTIVITY_CODE);
        }
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
            finish();
        } else if (requestCode == START_ACTIVITY_CODE && resultCode == Activity.RESULT_OK) {
            finish();
        }
    }

}
