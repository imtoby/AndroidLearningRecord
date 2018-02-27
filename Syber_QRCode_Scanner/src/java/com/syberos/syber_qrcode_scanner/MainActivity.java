package com.syberos.syber_qrcode_scanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends Activity {

    private TextView scanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanResult = findViewById(R.id.scan_result);
    }

    public void startScanner(View view) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
        intentIntegrator
                .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                .setPrompt("将二维码/条形码放入框内，即可自动扫描")//写那句提示的话
                .setOrientationLocked(false)//扫描方向固定
                .setCaptureActivity(CustomScannerActivity.class) // 设置自定义的 activity
                .initiateScan(); // 初始化扫描
    }

    //获取扫描的结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult
                = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {

            } else {
                // ScanResult 为获取到的字符串
                String ScanResult = intentResult.getContents();
                scanResult.setText(ScanResult);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
