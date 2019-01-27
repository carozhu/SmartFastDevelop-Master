package com.carozhu.fastdev.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.carozhu.fastdev.ContextHolder;
import com.carozhu.fastdev.receiver.NetWorkStateReceiver;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.tencent.mmkv.MMKV;

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

        //Init MMKV
        String rootDir = MMKV.initialize(this);
        System.out.println("mmkv root: " + rootDir);

        //初始化FileDownloader配置
        FileDownloader.setupOnApplicationOnCreate(this)
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15_000)
                        .readTimeout(15_000)
                ))
                .commit();

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
