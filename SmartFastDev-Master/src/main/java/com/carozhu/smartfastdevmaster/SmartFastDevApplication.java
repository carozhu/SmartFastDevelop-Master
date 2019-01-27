package com.carozhu.smartfastdevmaster;

import com.carozhu.fastdev.base.BaseApplication;
import com.carozhu.rxhttp.retrofitOkhttp.RetrofitOkhttpClient;

public class SmartFastDevApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //Net Init
        RetrofitOkhttpClient.init(this);
        RetrofitOkhttpClient.getInstance()
                .setBaseUrl("http://cc-test.fadi.com.cn")
                .configJacksonConverterFactory()
                .addLogInterceptor(true,"1.0");

    }
}
