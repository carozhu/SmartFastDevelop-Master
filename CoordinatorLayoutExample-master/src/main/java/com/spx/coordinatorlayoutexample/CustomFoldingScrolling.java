package com.spx.coordinatorlayoutexample;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowInsets;

import com.spx.coordinatorlayoutexample.adapter.SampleAdapter;
import com.spx.coordinatorlayoutexample.view.ElemeDetailView;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个示例是从
 * https://github.com/543441727/MyNestedScrolling
 * 复制过来的.
 * 对应的博文:
 * https://www.cnblogs.com/wjtaigwh/p/6398562.html
 *
 * 这个示例演示了通过NestedScrollingParent自定义一个可折叠嵌套滑动布局
 */
public class CustomFoldingScrolling extends BaseActivity {

    private ElemeDetailView edv;
    private View edv_title;
    private List<String> data = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.custom_myfolding_nestedscrolling_layout);
        setStatusBarTransparent();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        for (int i = 0; i < 50; i++) {
            data.add("CustomFoldingScrolling" + "第" + i + "个数据");
    }
        recyclerView.setAdapter(new SampleAdapter(data));

        //监听edv_content的位置改变，并改变edv_title的透明度
        edv = (ElemeDetailView) findViewById(R.id.edv);
        edv_title = findViewById(R.id.edv_title);
        edv.setListener(new ElemeDetailView.Listener() {
            @Override
            public void onContentPostionChanged(float fraction) {
                edv_title.setAlpha(1 - fraction);
            }
        });
    }

    /**
     * 把状态栏设成透明
     */
    private void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = CustomFoldingScrolling.this.getWindow().getDecorView();
            decorView.setOnApplyWindowInsetsListener((v, insets) -> {
                WindowInsets defaultInsets = v.onApplyWindowInsets(insets);
                return defaultInsets.replaceSystemWindowInsets(
                        defaultInsets.getSystemWindowInsetLeft(),
                        0,
                        defaultInsets.getSystemWindowInsetRight(),
                        defaultInsets.getSystemWindowInsetBottom());
            });
            ViewCompat.requestApplyInsets(decorView);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        }
    }
}
