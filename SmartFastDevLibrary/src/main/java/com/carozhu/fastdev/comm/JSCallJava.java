package com.carozhu.fastdev.comm;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;

import com.carozhu.fastdev.R;
import com.carozhu.fastdev.utils.NetworkUtil;
import com.carozhu.fastdev.widget.webview.CommWebView;
import com.carozhu.fastdev.widget.webview.WebViewCallback;

/**
 * Author: carozhu
 * Date  : On 2019/1/27
 * Desc  :
 */
public class JSCallJava {
    @JavascriptInterface
    public void refreshPager(Context context, CommWebView webview, CommWebView.NetErrorConfig netErrorConfig, WebViewCallback callback,String curWebUrl) {
        if (context != null) {
            /**
             * 4.4以上的webview，需要在子线程中调用js与java互相调用的代码
             */
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (NetworkUtil.isNetworkConnected(context)) {
                        webview. refresh();
                    } else {
                        if (CommWebView.NetErrorConfig.DEFAULT_BODY.equals(netErrorConfig)) {
                            webview.loadUrl(context.getResources().getString(R.string.comm_hdl_web_url_default));
                        } else {
                            webview.loadUrl(context.getResources().getString(R.string.comm_hdl_web_url_default2));
                        }
                        if (callback != null) {
                            callback.onError(202, "No Netwark", curWebUrl);
                        }
                    }
                }
            });
        }
    }
}
