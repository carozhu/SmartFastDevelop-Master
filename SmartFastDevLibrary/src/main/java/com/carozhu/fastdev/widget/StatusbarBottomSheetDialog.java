package com.carozhu.fastdev.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.carozhu.fastdev.R;
import com.carozhu.fastdev.helper.DisplayHelper;


/**
 * 解决弹出BottomSheetDialog状态栏黑色的问题
 */
public abstract class StatusbarBottomSheetDialog extends BottomSheetDialog {

    public StatusbarBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    public StatusbarBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    protected StatusbarBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int screenHeight = DisplayHelper.getScreenHeight(getContext());
        int dialogHeight = screenHeight ;
        setContentView(getLayoutId());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
        //getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(getContext(),R.color.translate));
        initView();

    }

    protected abstract @LayoutRes int getLayoutId();

    protected  abstract void initView();

}
