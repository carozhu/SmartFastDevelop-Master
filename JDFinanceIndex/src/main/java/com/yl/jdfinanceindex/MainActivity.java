package com.yl.jdfinanceindex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 京东金融首页效果
 * Created by yangle on 2017/2/5.
 */
public class MainActivity extends AppCompatActivity {

    private CircleImageView ivPortrait;
    private TextView tab;
    private StickyScrollView scrollView;

    private ViewGroup.MarginLayoutParams marginLayoutParams;
    private ViewGroup.MarginLayoutParams tabmarginLayoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        ivPortrait = (CircleImageView) findViewById(R.id.iv_portrait);
        scrollView = (StickyScrollView) findViewById(R.id.scrollView);
        //tab = (TextView) findViewById(R.id.tab);

        marginLayoutParams = new ViewGroup.MarginLayoutParams(ivPortrait.getLayoutParams());
       // tabmarginLayoutParams = new ViewGroup.MarginLayoutParams(tab.getLayoutParams());

        scrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                // 设置头像距离顶部的距离
                int top = dp2px(70) - y;
                if (top < dp2px(10)) {
                    // 固定在标题栏
                    marginLayoutParams.setMargins(dp2px(20), dp2px(5), 0, 0);
                   // tabmarginLayoutParams.setMargins(0, dp2px(100), 0, 0);
                } else {
                    // 向上移动
                    marginLayoutParams.setMargins(dp2px(20), dp2px(66) - y, 0, 0);
                    //tabmarginLayoutParams.setMargins(0, dp2px(100) - y, 0, 0);
                }

                // 根据向上滑动的距离设置头像的大小
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(marginLayoutParams);
                // 头像最大为66dp，最小为30dp
                int height = dp2px(66) - y < dp2px(40) ? dp2px(40) : dp2px(66) - y;
                layoutParams.height = height;
                layoutParams.width = height;
                ivPortrait.setLayoutParams(layoutParams);

               // FrameLayout.LayoutParams tablayoutParams = new FrameLayout.LayoutParams(tabmarginLayoutParams);
               // tab.setLayoutParams(tablayoutParams);
            }
        });
    }

    private int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}

