package com.carozhu.fastdev.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Author: carozhu
 * Date  : On 2018/8/2
 * Desc  :
 */
public interface IFragment<P> {
    /**
     * 布局ID
     * @return
     */
    int getLayoutId();

    /**
     * 创建prensenter
     *
     * @return <T extends BasePresenter> 必须是BasePresenter的子类
     */
    P initPresenter();

    /**
     *
     * @param rootView
     * @param savedInstanceState
     */
    void initView(View rootView, @Nullable Bundle savedInstanceState);

    /**
     * render data and UI
     */
    void render();

    /**
     * getClass().getSimpleName() + "  对用户第一次可见"
     */
    void onFragmentFirstVisible() ;


    /**
     * getClass().getSimpleName() + "  对用户可见"
     */
    void onFragmentResume() ;


    /**
     * getClass().getSimpleName() + "  对用户不可见"
     */
    void onFragmentPause();

    /**
     * recv Rxbus events
     *
     * @param rxPostEvent
     */
    void recvRxEvents(Object rxPostEvent);

    /**
     * @param connectType
     * @param connectName
     */
    void netReConnected(int connectType, String connectName);

    /**
     * Net Disconnect
     */
    void netDisConnected();

}
