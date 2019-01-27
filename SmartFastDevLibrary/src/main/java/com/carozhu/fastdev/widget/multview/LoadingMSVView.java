package com.carozhu.fastdev.widget.multview;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.carozhu.fastdev.R;
import com.carozhu.fastdev.base.BaseView;
import com.carozhu.fastdev.widget.LVCircularSmile;

/**
 * Author: carozhu
 * Date  : On 2018/9/18
 * Desc  :
 */
public class LoadingMSVView extends BaseView {
    private TextView tv_tips;
    private ImageView iv_backgd;
    private FrameLayout root;
    LVCircularSmile lvCircularSmile;

    public LoadingMSVView(@NonNull Context context) {
        this(context,null);
    }

    public LoadingMSVView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        inflate(R.layout.msv_loading_view, this, true);
        root = findViewById(R.id.root);
        tv_tips = findViewById(R.id.tv_tips);
        lvCircularSmile = findViewById(R.id.lv_circularSmile);
        iv_backgd = findViewById(R.id.iv_backgd);
        lvCircularSmile.setPaintColor(ContextCompat.getColor(getContext(),R.color.md_light_blue_400));
        lvCircularSmile.setStrokeWidth(6);
    }

    public LoadingMSVView setTipText(String tips){
        if (!TextUtils.isEmpty(tips)){
            tv_tips.setText(tips);
            tv_tips.setVisibility(VISIBLE);
        }else {
            tv_tips.setVisibility(GONE);
        }

        return this;
    }

    public LoadingMSVView setTipTextColor(@ColorInt int tipTextColor){
        if (tipTextColor!=0){
            tv_tips.setTextColor(tipTextColor);
        }
        return this;
    }

    public LoadingMSVView setLoadingBackgroundImageResource(@DrawableRes int resID){
        if (resID!=0){
            iv_backgd.setImageResource(resID);
        }
        return this;
    }


    public LoadingMSVView setLoadingBackgroundColor(@ColorInt int resID){
        if (resID!=0){
            root.setBackgroundColor(resID);
        }
        return this;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        lvCircularSmile.startAnim();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        lvCircularSmile.stopAnim();
    }

}
