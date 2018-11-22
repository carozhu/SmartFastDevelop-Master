package com.carozhu.fastdev.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.carozhu.fastdev.R;
import com.carozhu.fastdev.dialog.LVCircularSmile;
import com.carozhu.fastdev.widget.CircleProgressView;

/**
 * Author: carozhu
 * Date  : On 2018/8/31
 * Desc  : Loading Dialog
 */
public class LoadingDialog extends Dialog {
    /* 圆形转圈的加载进度条 */
    //private CircleProgressView progressView;
    /* 显示的文本*/
    private TextView msgText;
    private String msg;
    private Context mContext;
    private LVCircularSmile lvCircularSmile;

    private boolean cancel;


    public LoadingDialog(Context context, String msg, boolean cancel) {
        super(context, R.style.DialogTheme);
        this.mContext = context;
        this.msg = msg;
        this.cancel = cancel;
    }

    public static LoadingDialog showLoadingDialog(Context context, String tips, boolean cancel) {
        LoadingDialog loadingDialog = new LoadingDialog(context, tips, cancel);
        loadingDialog.show();
        return loadingDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(cancel);
        setCancelable(cancel);

        //progressView = findViewById(R.id.loading_dialog_circlProgress);
        msgText = findViewById(R.id.loading_dialog_text);
        if (!TextUtils.isEmpty(msg)) {
            msgText.setText(msg);
            msgText.setVisibility(View.VISIBLE);
        } else {
            msgText.setVisibility(View.GONE);
        }
        lvCircularSmile = findViewById(R.id.lv_circularSmile);
        lvCircularSmile.setPaintColor(ContextCompat.getColor(getContext(), R.color.md_light_blue_400));
        lvCircularSmile.setStrokeWidth(4);


        // getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // Window window = getWindow();
        // WindowManager.LayoutParams windowparams = window.getAttributes();
        // Rect rect = new Rect();
        // View view1 = window.getDecorView();
        // view1.getWindowVisibleDisplayFrame(rect);
        // WindowManager windowManager = ((Activity)
        // mContext).getWindowManager();
        // Display display = windowManager.getDefaultDisplay();
        // windowparams.width = (int) (display.getWidth() * 0.95);
        // window.setBackgroundDrawableResource(android.R.color.transparent);
        // window.setAttributes((android.view.WindowManager.LayoutParams)
        // windowparams);

        Resources resources = getContext().getResources();
        // 获得屏幕参数：主要是分辨率，像素等。
        DisplayMetrics dm = resources.getDisplayMetrics();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) (dm.widthPixels * 0.95);
        getWindow().setAttributes(params);

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // progressView.setVisibilyView(false);
            }
        });

        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                // progressView.setVisibilyView(true);
            }
        });
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        lvCircularSmile.startAnim();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        lvCircularSmile.stopAnim();
    }

    /**
     * 设置加载的提示文本
     *
     * @param msg
     */
    public void setMessage(String msg) {
        this.msgText.setText(msg);
    }

}
