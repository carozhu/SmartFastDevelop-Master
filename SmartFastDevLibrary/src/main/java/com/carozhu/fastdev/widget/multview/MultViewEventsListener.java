package com.carozhu.fastdev.widget.multview;

import android.view.View;

/**
 * Author: carozhu
 * Date  : On 2018/9/18
 * Desc  : mult -- 系列视图类接口callback
 */
public interface MultViewEventsListener {
    /**
     * 用于点击提示、图标刷新事件
     */
    void onEventRefresh();

    /**
     * 用于需要执行自定义动作的事件
     */
    void onClickAction();
}
