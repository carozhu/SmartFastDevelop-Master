package com.carozhu.smartfastdevmaster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.carozhu.fastdev.base.LazyLoadBaseFragment;

public class DebugFragment extends LazyLoadBaseFragment<LoginPresenter,UserContract.View> implements UserContract.View {


    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public LoginPresenter initPresenter() {
        return null;
    }

    @Override
    public void initView(View rootView, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void render() {

    }

    @Override
    public void onFragmentFirstVisible() {

    }

    @Override
    public void onFragmentResume() {

    }

    @Override
    public void onFragmentPause() {

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

    @Override
    public void showSuccess() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }
}
