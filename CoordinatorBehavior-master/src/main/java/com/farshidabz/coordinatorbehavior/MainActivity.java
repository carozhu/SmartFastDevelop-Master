package com.farshidabz.coordinatorbehavior;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        //设置显示返回键
        actionBar.setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        actionBar.setHomeButtonEnabled(true); //设置返回键可用
//
//        HandleProfileViewBehavior handleProfileViewBehavior =
//                new HandleProfileViewBehavior(findViewById(R.id.activity_main));
    }
}
