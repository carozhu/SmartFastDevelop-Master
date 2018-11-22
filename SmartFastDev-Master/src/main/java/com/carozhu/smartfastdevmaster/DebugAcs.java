package com.carozhu.smartfastdevmaster;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.carozhu.fastdev.base.BaseActivity;
import com.carozhu.fastdev.mvp.BasePresenter;

/**
 * Author: carozhu
 * Date  : On 2018/8/1
 * Desc  :
 */
public class DebugAcs extends BaseActivity {

    @Override
    public int getLayoutId(@Nullable Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public Object initPresenter() {
        return null;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void render() {

    }

    @Override
    public void recvRxEvents(Object rxPostEvent) {

    }

    @Override
    public void netReConnected(int connectType, String connectName) {

    }

    @Override
    public void netDisConnected() {

    }
}
