package com.carozhu.fastdev.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.carozhu.fastdev.R;
import com.carozhu.fastdev.widget.LVCircularSmile;

public class LoadingDialog extends Dialog {
    private TextView tipsTV;
    private LVCircularSmile smaile;

    private int strokeWidth = 4;
    private int paintColor = R.color.md_blue_A400;
    private int tipsColor = R.color.md_blue_grey_300;
    private String tips = "";
    private boolean cancelable = false;
    private boolean CanceledOnTouchOutside = false;

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.alert_dialog_style);
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

    }

    public LoadingDialog setStatuTip(String statuTip) {
        tipsTV.setVisibility(View.VISIBLE);
        tipsTV.setText(statuTip);
        return this;
    }


    @Override
    public void dismiss() {
        super.dismiss();
        if (smaile != null) {
            smaile.stopAnim();
            smaile = null;
        }
    }


    /**
     * 调用完Builder类的create()方法后显示该对话框的方法
     */
    @Override
    public void show() {
        super.show();
        show(this);
    }

    private void show(LoadingDialog mLoadingDialog){
        //提示语
        if (!TextUtils.isEmpty(mLoadingDialog.tips)){
            mLoadingDialog.tipsTV.setText(tips);
            mLoadingDialog.tipsTV.setVisibility(View.VISIBLE);
        }else {
            mLoadingDialog.tipsTV.setVisibility(View.GONE);
        }
        //Tips Color
        if (mLoadingDialog.tipsColor>0){
            mLoadingDialog.tipsTV.setTextColor(ContextCompat.getColor(getContext(), mLoadingDialog.tipsColor));
        }else {
            mLoadingDialog.tipsTV.setTextColor(ContextCompat.getColor(getContext(), tipsColor));
        }
       //strokeWidth
        if (mLoadingDialog.strokeWidth>0){
            mLoadingDialog.smaile.setStrokeWidth(mLoadingDialog.strokeWidth);
        }else {
            mLoadingDialog.smaile.setStrokeWidth(strokeWidth);
        }
        //paintColor
        if (mLoadingDialog.paintColor>0){
            mLoadingDialog.smaile.setPaintColor(ContextCompat.getColor(getContext(),mLoadingDialog.paintColor));
        }else {
            mLoadingDialog.smaile.setPaintColor(ContextCompat.getColor(getContext(),paintColor));
        }

        //default false
        mLoadingDialog.setCancelable(mLoadingDialog.cancelable);
        mLoadingDialog.setCanceledOnTouchOutside(mLoadingDialog.CanceledOnTouchOutside);

        //start Aniamation
        mLoadingDialog.smaile.startAnim();
    }

    public static class Builder {
        private LoadingDialog mLoadingDialog;

        public Builder(Context context) {
            mLoadingDialog = new LoadingDialog(context);
        }

        /**
         * 设置提示
         *
         * @param tips
         * @return
         */
        public Builder setTips(String tips) {
            mLoadingDialog.tips = tips;
            return this;
        }

        /**
         * 设置笑脸paint width
         *
         * @param strokeWidth
         * @return
         */
        public Builder setStrokeWidth(int strokeWidth) {
            mLoadingDialog.strokeWidth = strokeWidth;
            return this;
        }

        /**
         * 设置笑脸paint color
         *
         * @param paintColor
         * @return
         */
        public Builder setPaintColor(int paintColor) {
            mLoadingDialog.paintColor = paintColor;
            return this;
        }

        /**
         * 设置提示字体
         *
         * @param tipsColor
         * @return
         */
        public Builder setTipsColor(int tipsColor) {
            mLoadingDialog.tipsColor = tipsColor;
            return this;
        }

        /**
         * 设置是否可以点击返回键取消
         *
         * @param cancelable
         * @return
         */
        public Builder setCancelable(boolean cancelable) {
            mLoadingDialog.cancelable = cancelable;
            return this;
        }

        /**
         * 设置是否可以点Touch外部取消
         *
         * @param CanceledOnTouchOutside
         * @return
         */
        public Builder setCanceledOnTouchOutside(boolean CanceledOnTouchOutside) {
            mLoadingDialog.CanceledOnTouchOutside = CanceledOnTouchOutside;
            return this;
        }


        /**
         * 通过Builder类设置完属性后构造对话框的方法
         */
        public LoadingDialog create() {
            return mLoadingDialog;
        }


    }

}
