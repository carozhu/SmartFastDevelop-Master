package com.carozhu.fastdev.widget.refresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * Author: carozhu
 * Date  : On 2018/12/13
 * Desc  :
 */
public class BaseSwipeRefreshLayout extends SwipeRefreshLayout {
    public BaseSwipeRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public BaseSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
