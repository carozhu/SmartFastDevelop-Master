package com.carozhu.smartfastdevmaster.BottomSheetDialogSimple;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.carozhu.fastdev.helper.DisplayHelper;
import com.carozhu.smartfastdevmaster.R;


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
        int screenHeight = getScreenHeight(getContext());
        int dialogHeight = screenHeight;
        setContentView(getLayoutId());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
        //getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTranslate));
        initView();

    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = DisplayHelper.getScreenHeight(context);
        return height;
    }

    protected abstract @LayoutRes
    int getLayoutId();

    protected abstract void initView();


}
