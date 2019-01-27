package com.carozhu.fastdev.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Author: carozhu
 * Date  : On 2018/8/13
 * Desc  :
 */
public class BaseView extends FrameLayout {
    public BaseView(@NonNull Context context) {
        super(context);
    }
    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public LayoutInflater getLayoutInflater(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater;
    }

    public void inflate(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot){
        LayoutInflater inflater = getLayoutInflater(getContext());
        inflater.inflate(resource, root, attachToRoot);
    }
}
