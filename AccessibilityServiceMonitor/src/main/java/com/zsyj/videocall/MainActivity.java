package com.zsyj.videocall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.zsyj.videocall.service.AccessibilityServiceMonitor;
import com.zsyj.videocall.util.AccessibilitUtil;

import androidx.appcompat.app.AppCompatActivity;
//import com.tbruyelle.rxpermissions2.RxPermissions;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    String TAG = "MainActivity";
    private Button btnSettings;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSettings = (Button) findViewById(R.id.btn_settings);
        btnSettings.setOnClickListener(this);
        context = this;
        startService();
        //Debug 检测是否开启了辅助服务
        boolean isOpen = AccessibilityHelper.isAccessibilitySettingsOn(AccessibilityServiceMonitor.class.getCanonicalName(), context);
        Log.d(TAG, "Accessibility is open :" + isOpen);
        if (!isOpen) {
            //打开辅助服务设置界面
            AccessibilityHelper.openAccessibility(AccessibilityServiceMonitor.class.getCanonicalName(), context);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isOpen = AccessibilityHelper.isAccessibilitySettingsOn(AccessibilityServiceMonitor.class.getCanonicalName(), context);
        if (isOpen){

//            new Handler().postDelayed(() -> {
//                new RxPermissions( MainActivity.this).request(
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_PHONE_STATE,
//                        Manifest.permission.SYSTEM_ALERT_WINDOW)
//                        .subscribe(granted -> {
//                            if (granted) {
//
//                            } else {
//                                // Oups permission denied
//
//                            }
//                        });
//            }, 500);
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_settings:
                AccessibilitUtil.showSettingsUI(this);
                break;
        }
    }



    private void startService() {
        Intent mIntent = new Intent(this, AccessibilityServiceMonitor.class);
        startService(mIntent);
    }



}
