package com.carozhu.smartfastdevmaster;

import com.carozhu.fastdev.base.BaseActivity;

public abstract  class BaseAppActivity extends BaseActivity {
    @Override
    protected void onStart() {
        super.onStart();
        //add 统计
    }

    @Override
    protected void onResume() {
        super.onResume();
        //add 统计
    }

    @Override
    protected void onPause() {
        super.onPause();
        //add 统计

    }


    @Override
    protected void onStop() {
        super.onStop();
        //add 统计
    }
}
