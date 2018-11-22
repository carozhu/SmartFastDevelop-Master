package com.carozhu.fastdev.widget;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 可禁止左右滑动
 */
public class ProhibitScrollableViewPager extends ViewPager {

    //是否可以滑动
    private boolean isCanScroll = true;

    public ProhibitScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProhibitScrollableViewPager(Context context) {

        super(context);
    }


    /*----------禁止左右滑动------------------*/
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (isCanScroll) {
            return super.onInterceptTouchEvent(arg0);
        } else {
            return false;
        }

    }

    /**
     * 设置 是否可以滑动
     *
     * @param isCanScroll
     */
    public void setScrollble(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }
}