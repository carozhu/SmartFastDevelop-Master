package com.carozhu.fastdev.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.carozhu.fastdev.R;

public class SmileLoadingDialog extends Dialog {
    private SmileLoadingDialog smileLoadingDialog;
    private String loadingTips;
    private TextView tipsTV;
    private LVCircularSmile smaile;

    public SmileLoadingDialog(@NonNull Context context, String loadingTips) {
        super(context, R.style.alert_dialog_style);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        this.loadingTips = loadingTips;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_smile_loading);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);

        tipsTV = findViewById(R.id.loading_tips);
        smaile = findViewById(R.id.lv_circularSmile);
        smaile.startAnim();

        if (TextUtils.isEmpty(loadingTips)) {
            tipsTV.setText("Loading...");
        } else {
            tipsTV.setText(loadingTips);
        }
    }

    public SmileLoadingDialog setStatuTip(String statuTip){
        tipsTV.setVisibility(View.VISIBLE);
        tipsTV.setText(statuTip);
        return this;
    }

    public SmileLoadingDialog setStatuTip(String statuTip,@ColorInt int color){
        tipsTV.setVisibility(View.VISIBLE);
        tipsTV.setText(statuTip);
        if (color>0){
            tipsTV.setTextColor(color);
        }
        return this;
    }

    /**
     *
     * @return
     */
    public SmileLoadingDialog hideTips(){
        tipsTV.setVisibility(View.GONE);
        return this;
    }

    /**
     *
     * @param color
     * @return
     */
    public SmileLoadingDialog setSmileColor(@ColorInt int color){
        smaile.setPaintColor(color);
        return this;
    }

    @Override
    public void dismiss(){
        super.dismiss();
        if (smaile!=null){
            smaile.stopAnim();
            smaile = null;
        }
    }

    /**
     * show Default Dialog
     * @param loadingTips
     */
    public static SmileLoadingDialog show(Context context, String loadingTips) {
        SmileLoadingDialog smileLoadingDialog = new SmileLoadingDialog(context, loadingTips);
        smileLoadingDialog.show();
        return smileLoadingDialog;
    }

}
