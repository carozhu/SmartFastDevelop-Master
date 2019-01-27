package com.carozhu.fastdev.widget.rv;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Author: carozhu
 * Date  : On 2018/12/13
 * Desc  : 基本的RecyclerView
 */
public class BaseRecyclereView extends RecyclerView {
    public BaseRecyclereView(Context context) {
        super(context);
    }

    public BaseRecyclereView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRecyclereView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
