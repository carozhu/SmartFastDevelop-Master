package com.carozhu.smartfastdevmaster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.carozhu.fastdev.base.BaseActivity;
import com.carozhu.fastdev.helper.StatusBarHelper;
import com.carozhu.fastdev.receiver.NetWorkStateReceiver;
import com.carozhu.fastdev.rx.RxSchedulersHelper;
import com.carozhu.fastdev.utils.AppInfoUtil;
import com.carozhu.fastdev.utils.DateUtil;
import com.carozhu.fastdev.utils.DeviceUUIDFactory;
import com.carozhu.fastdev.utils.RandomHelper;
import com.carozhu.rxhttp.ApiHelper;
import com.carozhu.smartfastdevmaster.RV.BasicGridRvActivity;


import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity<LoginPresenter, UserContract.View> implements UserContract.View {
    @BindView(R.id.button)
    Button button;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    int mStatusBarColor = 0;

    @Override
    public int getLayoutId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter(this);

    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mStatusBarColor = getResources().getColor(R.color.colorAccent);

    }

    @Override
    public void render() {

        NetWorkStateReceiver.getInstance().registerReceiver();
        mPresenter.login();
        String UUID = new DeviceUUIDFactory(context).getDeviceUuid();
        Log.i("LOGIN", "UUID ------ " + UUID);
        StatusBarHelper.setStatusBarDarkMode(this);

        //背景作为root background时，设置setTranslucent就可以达到效果
        StatusBarHelper.setTranslucent(this, 0);
        swipeBack(true);
        mStatusBarColor = ContextCompat.getColor(context, android.R.color.transparent);
        toolbar.setBackgroundColor(mStatusBarColor);

        Map maps = new HashMap();
        maps.put("pid", "1");
        maps.put("sid", "1");
        maps.put("version", String.valueOf(AppInfoUtil.getVerName(context)));
        maps.put("vercode", String.valueOf(AppInfoUtil.getVerCode(context)));
        maps.put("sver", "4.5");
        maps.put("noncestr", RandomHelper.getRandomStr(12));
        maps.put("timestamp", String.valueOf(DateUtil.getCurrentTimeMillis() / 1000));
        maps.put("uuid", "1");
        maps.put("key", "1");

        HabitAPIService habitAPIService = ApiHelper.getInstance().getAPIService(HabitAPIService.class);
        Disposable disposable =  habitAPIService.getConfig((Map<String, String>) maps)
                .compose(RxSchedulersHelper.io_main())
                .compose(bindToLifecycle())
                .subscribe(configBean -> {

                    weakHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            button.setText("请求数据成功");
                            Toast.makeText(activity, "请求数据成功", Toast.LENGTH_LONG).show();
                        }
                    }, 1000);
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        Toast.makeText(activity, "@@@@@ when parse server data. occur erro !", Toast.LENGTH_LONG).show();
                    }
                });

        weakHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.finish();
            }
        }, 1);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetWorkStateReceiver.getInstance().unRegisterReceiver();
    }

    @Override
    public void recvRxEvents(Object rxPostEvent) {

    }

    @OnClick({R.id.button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                startActivity(context, BasicGridRvActivity.class);
                break;
        }
    }


    @Override
    public void netReConnected(int connectType, String connectName) {
        Toast.makeText(context, "------netReConnected", Toast.LENGTH_LONG).show();
    }

    @Override
    public void netDisConnected() {
        Toast.makeText(context, "-----netDisConnected", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccess() {
        Toast.makeText(context, "LOGIN showSuccess !!! ", Toast.LENGTH_LONG).show();
    }



    @Override
    public void showLoading(String loadingTips) {

    }

    @Override
    public void dismissLoading() {

    }





}
