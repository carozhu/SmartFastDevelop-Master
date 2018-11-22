package com.carozhu.fastdev.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carozhu.fastdev.R;
import com.carozhu.fastdev.base.BaseRootView;
import com.carozhu.fastdev.helper.DisplayHelper;

/**
 * Author: carozhu
 * Date  : On 2018/8/13
 * Desc  : 智能Toolbar
 * TODO 1 ：常规需求的标题栏 （左边返回按钮 + 中间标题）
 * TODO 2 : 中间自定义View  右边自定义View
 * TODO 3 : 统一配置颜色大小背景默认
 */
public class SmartToolbar extends BaseRootView {
    private ActionBar actionBar;
    private Toolbar toolbar;
    private TextView titleTv;

    private int titleBarColor;                // 标题栏背景颜色
    private int titleBarHeight;               // 标题栏高度
    private boolean showNavigation;           // 是否显示返回键
    private String centerTitleText;           // 中标题
    private int centerTitleColor;             // 中标题颜色
    private float centerTitleSize;            // 中标题字体大小
    private int centerTitleType;              // 中标题字体类型
    private @DrawableRes
    int navigationResId;                      // 自定义返回键图标

    private static final int CENTER_TEXT_TYPE_NORMAL = 0; // 默认类型

    public SmartToolbar(Context context) {
        super(context);
        initView();
    }

    public SmartToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        loadAttributes(attrs);
        configAttributeSet();
    }

    private void loadAttributes(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SmartToolbar);
        titleBarColor = array.getColor(R.styleable.SmartToolbar_titleBarColor, Color.parseColor("#FFFFFF"));
        //titleBarHeight = (int) array.getDimension(R.styleable.SmartToolbar_titleBarHeight, DisplayHelper.dip2px(getContext(), android.R.attr.actionBarSize));
        titleBarHeight = (int) array.getDimension(R.styleable.SmartToolbar_titleBarHeight, DisplayHelper.dip2px(getContext(), 46));
        showNavigation = array.getBoolean(R.styleable.SmartToolbar_showNavigation, true);
        centerTitleText = array.getString(R.styleable.SmartToolbar_centerTitleText);
        centerTitleColor = array.getColor(R.styleable.SmartToolbar_centerTitleColor, getResources().getColor(R.color.md_black_1000));
        centerTitleSize = array.getDimension(R.styleable.SmartToolbar_centerTitleSize, 16);
        centerTitleType = array.getInt(R.styleable.SmartToolbar_centerTitleType, CENTER_TEXT_TYPE_NORMAL);
        navigationResId = array.getResourceId(R.styleable.SmartToolbar_navigationIcon, 0);//navigationResId R.drawable.ic_back_white_24dp
    }

    private void initView() {
        inflate(R.layout.view_smart_toolbar, this, true);
        toolbar = findViewById(R.id.toolbar);
        titleTv = findViewById(R.id.titleTv);

        //配置支持Toolbar
        surportToolbar();
    }

    private void configAttributeSet() {
        setToolbarHeight(titleBarHeight);
        setToolbarBackGroud(titleBarColor);
        if (TextUtils.isEmpty(centerTitleText)) {
            setTitle("Title");
        } else {
            setTitle(centerTitleText);
        }
        setTitleColor(centerTitleColor);
        setTitleSize(centerTitleSize);
        setTitleTypeface(CENTER_TEXT_TYPE_NORMAL);
        //无设置时，使用系统Toolbar默认的返回键
        if (navigationResId > 0) {
            setNavigationIcon(navigationResId);
        }
        //默认显示返回键
        showNavigation(showNavigation);

        //配置默认点击返回
        setNavigationOnClickBack();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    /**
     * @return
     */
    public SmartToolbar surportToolbar() {
        Context context = getContext();
        if (context instanceof Activity) {
            AppCompatActivity mAppCompatActivity = (AppCompatActivity) context;
            mAppCompatActivity.setSupportActionBar(toolbar);
            actionBar = mAppCompatActivity.getSupportActionBar();
            if (actionBar != null) {
                //设置默认禁用Toolbar自带的标题
                actionBar.setDisplayShowTitleEnabled(false);
            }
        }
        return this;
    }

    /**
     * @param showNavigation 是否显示左icon 返回键
     *                       默认显示
     * @return
     */
    public SmartToolbar showNavigation(boolean showNavigation) {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(showNavigation);
        }

        return this;
    }

    /**
     * 使用系统默认的Toolbar标题
     *
     * @param title
     */
    public SmartToolbar setDefaultToolbarTitle(String title) {
        if (actionBar == null) {
            throw new RuntimeException("actionBar is null !,please involk surportToolbar to init");
        }
        actionBar.setTitle(title);
        actionBar.setDisplayShowTitleEnabled(true);
        return this;
    }

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public SmartToolbar setTitle(String title) {
        titleTv.setText(title);
        return this;
    }

    /**
     * 设置标题颜色
     *
     * @param color
     * @return
     */
    public SmartToolbar setTitleColor(int color) {
        titleTv.setTextColor(color);
        return this;
    }

    /**
     * 设置颜色
     *
     * @param size
     * @return
     */
    public SmartToolbar setTitleSize(float size) {
        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        return this;
    }

    /**
     * 设置defaultFromStyle
     *
     * @param style Typeface.NORMAL- 0 Typeface.BOLD - 1
     * @return
     */
    public SmartToolbar setTitleTypeface(int style) {
        titleTv.setTypeface(Typeface.defaultFromStyle(style));
        return this;
    }

    /**
     * 设置Appearance
     *
     * @param
     * @return
     */
    public SmartToolbar setTitleAppearance(@StyleRes int resId) {
        titleTv.setTextAppearance(getContext(), resId);
        return this;
    }

    /**
     * 设置返回finish。若要拦截back，请重写activity中的finish();或使用以下方法
     *
     * @return
     */
    public SmartToolbar setNavigationOnClickBack() {
        toolbar.setNavigationOnClickListener(v -> {
            Context context = getContext();
            if (context instanceof Activity) {
                Activity mAppCompatActivity = (Activity) context;
                mAppCompatActivity.finish();
            }
        });
        return this;
    }

    /**
     * @param onClickListener
     * @return
     */
    public SmartToolbar setNavigationOnClickBack(OnClickListener onClickListener) {
        toolbar.setNavigationOnClickListener(onClickListener);
        return this;
    }

    /**
     * 若不使用系统自带的Navigation icon ，可使用如下方法设置自己的返回键
     *
     * @param resId
     * @return
     */
    public SmartToolbar setNavigationIcon(@DrawableRes int resId) {
        toolbar.setNavigationIcon(resId);
        return this;
    }

    /**
     * 重置Toolbar高度
     *
     * @param height
     */
    protected void setToolbarHeight(int height) {
        ViewGroup.LayoutParams v = toolbar.getLayoutParams();
        v.height = height;
        toolbar.setLayoutParams(v);
    }


    /**
     * 设置背景色
     *
     * @param titleBarColor
     */
    protected void setToolbarBackGroud(int titleBarColor) {
        toolbar.setBackgroundColor(titleBarColor);
    }


    public ActionBar getActionBar() {
        return actionBar;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

}
