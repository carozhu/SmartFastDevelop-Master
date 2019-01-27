package com.carozhu.fastdev.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Author: carozhu
 * Date  : On 2018/8/2
 * Desc  :
 */
public interface IBaseDialog<P> {


    /**
     * 初始化 View
     *
     * @param dialog
     * @return
     */
    @LayoutRes
    int getLayoutId(Dialog dialog);

    /**
     * 初始化 View 视图组件
     *
     * @param dialogView
     */
    void initView(View dialogView,Dialog dialog);

    /**
     * 创建prensenter
     *
     * @return <T extends BasePresenter> 必须是BasePresenter的子类
     */
    P initPresenter();

    /**
     * 渲染数据
     */
    void render();

    /**
     *
     */
    void onShowen();

    void onHidden();


    /**
     * recv Rxbus events
     * 接收Rxbus消息总线分发
     *
     * @param rxPostEvent
     */
    void recvRxEvents(Object rxPostEvent);

    /**
     * 网络已连接
     *
     * @param connectType
     * @param connectName
     */
    void netReConnected(int connectType, String connectName);

    /**
     * 网络断开
     */
    void netDisConnected();
}
