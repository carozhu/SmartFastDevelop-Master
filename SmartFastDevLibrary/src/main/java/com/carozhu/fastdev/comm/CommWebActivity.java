package com.carozhu.fastdev.comm;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carozhu.fastdev.R;
import com.carozhu.fastdev.base.BaseActivity;
import com.carozhu.fastdev.helper.StatusBarHelper;
import com.carozhu.fastdev.widget.webview.AndroidBug5497Workaround;
import com.carozhu.fastdev.widget.webview.CommWebView;
import com.carozhu.fastdev.widget.webview.WebViewCallback;

import butterknife.OnClick;


/**
 * Author: carozhu
 * Date  : On 2019/1/27
 * Desc  : 通用WebActivity
 */
public class CommWebActivity extends BaseActivity {
    private String TAG = CommWebActivity.class.getSimpleName();

    LinearLayout ll_headerbar;
    CommWebView webview;
    TextView tv_title;
    String title;
    int topbarColor;
    String url;

    public static void startCommWebViewActivity(Activity activity, String title,int topbarColor,String url) {
        Intent intent = new Intent(activity, CommWebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("topbarColor", topbarColor);
        bundle.putString("url", url);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }


    @Override
    protected void beforeSetContent() {
        super.beforeSetContent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        //代码设置透明状态栏：
        setTransparentForWindow(this);
    }



    @Override
    public int getLayoutId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_comm_web;
    }

    @Override
    public Object initPresenter() {
        return null;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        // 获得状态栏高度
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statuH = getResources().getDimensionPixelSize(resourceId);
        LinearLayout statusView = (LinearLayout) findViewById(R.id.status_view);
        statusView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, statuH));
        StatusBarHelper.setTranslucentForImageView(activity, 0, null);

        ll_headerbar = (LinearLayout) findViewById(R.id.ll_headerbar);
        tv_title = (TextView)findViewById(R.id.tv_title);

        webview = (CommWebView)findViewById(R.id.webview);
        webview.setNetErrorConfig(CommWebView.NetErrorConfig.DEFAULT_BUTTON);
        webview.getWebview().getSettings().setDomStorageEnabled(true);

        AndroidBug5497Workaround.assistActivity(this);

        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        topbarColor = bundle.getInt("topbarColor");
        url = bundle.getString("url");

        if (!TextUtils.isEmpty(title)){
            tv_title.setText(title);
        }
        if (topbarColor>0){
            ll_headerbar.setBackgroundColor(topbarColor);
        }

        findViewById(R.id.fl_back).setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public void render() {
        webview.setCurWebUrl(url)
                //.addJavascriptInterface(new JSCallJava().refreshPager(context), "NativeObj")
                .startCallback(new WebViewCallback() {
                    @Override
                    public void onStart() {
                        Log.i(TAG, "开始调用了");
                        showLoading("");
                    }

                    @Override
                    public void onProgress(int curProgress) {
                        Log.e(TAG, "onProgress: " + curProgress);
                        if (curProgress > 80) {//加载完成80%以上就可以隐藏了，防止部分网页不能
                            dismissLoading();
                            //tvTitle.setText(wv_main.getWebTitle());
                        }
                    }

                    @Override
                    public void onError(int errorCode, String description, String failingUrl) {
                        super.onError(errorCode, description, failingUrl);
                        Log.e(TAG, errorCode + " \t" + description + "\t" + failingUrl);
                    }
                });
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
    protected void onDestroy() {
        webview.onDestroy();
        super.onDestroy();

    }

    /**
     * 监听返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webview.canGoBack()) {
                webview.goBack();//返回上一个页面
                return true;
            }
        }
        return false;
    }
}
