package com.carozhu.fastdev.widget.multview;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.carozhu.fastdev.R;
import com.carozhu.fastdev.base.BaseView;

/**
 * Author: carozhu
 * Date  : On 2018/9/18
 * Desc  :
 */
public class EmptyErrorMultView extends BaseView {
    private ImageView iv_img;
    private ImageView iv_backgd;
    private TextView tv_tips;
    private TextView tv_avtion;
    private TextView tv_check_netconnet;
    private FrameLayout root;

    private MultViewEventsListener multViewEventsListener;

    public EmptyErrorMultView(@NonNull Context context) {
        this(context, null);
    }

    public EmptyErrorMultView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        inflate(R.layout.msv_error_empty_view, this, true);
        root = findViewById(R.id.root);
        iv_img = findViewById(R.id.iv_img);
        iv_backgd = findViewById(R.id.iv_backgd);
        tv_tips = findViewById(R.id.tv_tips);
        tv_avtion = findViewById(R.id.tv_avtion);
        tv_check_netconnet = findViewById(R.id.tv_check_netconnet);

        iv_img.setOnClickListener(v -> {
            if (multViewEventsListener != null) {
                multViewEventsListener.onEventRefresh();
            }
        });

        tv_tips.setOnClickListener(v -> {
            if (multViewEventsListener != null) {
                multViewEventsListener.onEventRefresh();
            }
        });
        tv_avtion.setOnClickListener(v -> {
            if (multViewEventsListener != null) {
                multViewEventsListener.onClickAction();
            }
        });

        tv_check_netconnet.setOnClickListener(v -> {
            //跳转到网络设置页面
            getContext().startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
        });
    }

    public EmptyErrorMultView setTipText(String tips) {
        if (!TextUtils.isEmpty(tips)) {
            tv_tips.setText(tips);
            tv_tips.setVisibility(VISIBLE);
        } else {
            tv_tips.setVisibility(GONE);
        }

        return this;
    }

    public EmptyErrorMultView doCheckNetWork(boolean needChecknet) {
        if (needChecknet) {
            tv_check_netconnet.setVisibility(VISIBLE);
        } else {
            tv_check_netconnet.setVisibility(GONE);
        }
        return this;
    }

    public EmptyErrorMultView setTipTextColor(@ColorInt int color) {
        tv_tips.setTextColor(color);
        return this;
    }


    public EmptyErrorMultView setActionText(String actionName) {
        if (!TextUtils.isEmpty(actionName)) {
            tv_avtion.setText(actionName);
            tv_avtion.setVisibility(VISIBLE);
        } else {
            tv_avtion.setVisibility(GONE);
        }

        return this;
    }

    public EmptyErrorMultView setActionTextColor(@ColorInt int color) {
        if (color != 0) {
            tv_avtion.setTextColor(color);
        }

        return this;
    }

    public EmptyErrorMultView setActionBackground(@DrawableRes int resId) {
        if (resId != 0) {
            tv_avtion.setBackgroundResource(resId);
        }

        return this;
    }

    public EmptyErrorMultView setTipsImage(@DrawableRes int resId) {
        if (resId != 0) {
            iv_img.setImageResource(resId);
        }
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public EmptyErrorMultView setTipsBackground(Drawable drawable) {
        if (drawable != null) {
            tv_tips.setBackground(drawable);
        }

        return this;
    }

    public EmptyErrorMultView setBackground(@DrawableRes int resId) {
        if (resId != 0) {
            iv_backgd.setImageResource(resId);
        }
        return this;
    }

    public void setBackgroundColor(@ColorRes int resID) {
        if (resID != 0) {
            root.setBackgroundColor(resID);
        }

    }

    public EmptyErrorMultView setMultViewEventsListener(MultViewEventsListener multViewEventsListener) {
        this.multViewEventsListener = multViewEventsListener;
        return this;
    }


}
