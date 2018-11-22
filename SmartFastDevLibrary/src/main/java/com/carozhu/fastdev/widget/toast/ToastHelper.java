package com.carozhu.fastdev.widget.toast;

import android.content.Context;

/**
 * 若该工具类不满足，可参考使用Toasty 第三方库 ：https://github.com/GrenderG/Toasty
 */
public class ToastHelper {

    /**
     *
     * @param context
     * @param msg
     */
    public static void showShort(Context context, final String msg) {
        try {
            IToast.show(context, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param context
     * @param msg
     */
    public static void showLong(Context context, final String msg) {
        try {
            IToast.showLong(context, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
