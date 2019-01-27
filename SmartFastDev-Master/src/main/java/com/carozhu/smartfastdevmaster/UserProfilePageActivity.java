package com.carozhu.smartfastdevmaster;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
/**
 * Author: carozhu
 * Date  : On 2018/12/4
 * Desc  : 用户个人主页
 */
public class UserProfilePageActivity extends BaseAppActivity implements AppBarLayout.OnOffsetChangedListener {
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.6f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;

    @BindView(R.id.main_linearlayout_title)
    LinearLayout mTitleContainer;
    @BindView(R.id.toolbar_parent)
    LinearLayout toolbar_parent;
    @BindView(R.id.status_view)
    LinearLayout statusView;
    @BindView(R.id.main_textview_title)
    TextView mTitle;
    @BindView(R.id.main_appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;


    @Override
    protected void beforeSetContent() {
        super.beforeSetContent();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 禁用横屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }


    @Override
    public int getLayoutId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_userprofile;
    }

    @Override
    public Object initPresenter() {
        return null;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        swipeBack(true);
        // 获得状态栏高度
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statuH = getResources().getDimensionPixelSize(resourceId);
        statusView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, statuH));

        mAppBarLayout.addOnOffsetChangedListener(this);
        //mToolbar.inflateMenu(R.menu.userprofile_menu);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
    }

    @Override
    public void render() {

    }

    @Override
    public void recvRxEvents(Object rxPostEvent) {

    }

    @Override
    public void netReConnected(int connectType, String connectName) {

    }

    @Override
    public void netDisConnected() {

    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;
        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(appBarLayout, percentage);

    }

    private void handleToolbarTitleVisibility(AppBarLayout appBarLayout, float percentage) {
        int verticalOffset = appBarLayout.getTotalScrollRange();
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
                toolbar_parent.setBackgroundColor(changeAlpha(getResources().getColor(R.color.colorAccent), Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange()));
            }

        } else {
            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
                toolbar_parent.setBackgroundColor(changeAlpha(getResources().getColor(R.color.translate), Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange()));
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.menu_focus:
//                ToastHelper.showLong(context, "focus");
//                break;
        }
        return true;
    }

    /**
     * 根据百分比改变颜色透明度
     */
    public int changeAlpha(int color, float fraction) {
        /*
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
        */
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, 0, 128, 0);
    }

}
