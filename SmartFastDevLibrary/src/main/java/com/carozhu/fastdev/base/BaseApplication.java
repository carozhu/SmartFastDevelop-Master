package com.carozhu.fastdev.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.carozhu.fastdev.ContextHolder;
import com.carozhu.fastdev.receiver.NetWorkStateReceiver;
public class BaseApplication extends Application {
    private static Application context;
    private static BaseApplication mInst;

    @Override
    public void onCreate(){
        super.onCreate();
        context = this;
        mInst = this;
        ContextHolder.initial(this);
        NetWorkStateReceiver.getInstance().init(this,false);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

    public static  Application getContext(){
        return context;
    }
    public static BaseApplication getInst() { return mInst; }

}
