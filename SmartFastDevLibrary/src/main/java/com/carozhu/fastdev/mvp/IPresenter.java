package com.carozhu.fastdev.mvp;

import android.app.Activity;

public interface IPresenter <V extends IContractView>{
    /**
     * init operation
     */
    void start();

    /**
     *  {@link Activity#onDestroy()}
     */
    void onDestroy();

    /**
     * presenter和对应的view绑定
     * @param mvpView  目标view
     */
    void attachView(V mvpView);

    /**
     * presenter与view解绑
     */
    void detachView();
}
