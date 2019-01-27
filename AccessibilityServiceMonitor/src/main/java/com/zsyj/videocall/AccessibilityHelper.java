package com.zsyj.videocall;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * Author: carozhu
 * Date  : On 2018/12/29
 * Desc  : Accessibility 帮助类
 */
public class AccessibilityHelper {
    private static final String TAG = AccessibilityHelper.class.getSimpleName();

    /**
     * 检测辅助功能开关是否打开
     *
     * @param mContext                 上下文
     * @param accessibilityServiceName 指定辅助服务名字
     * @return
     */
    public static boolean isAccessibilitySettingsOn(String accessibilityServiceName, Context mContext) {
        int accessibilityEnabled = 0;
        final String service = mContext.getPackageName() + "/" + accessibilityServiceName;
        Log.i(TAG, "service:" + service); // formate: app packagename/packagename.service.AccessibilityServiceMonitor

        try {
            accessibilityEnabled = Settings.Secure.getInt(mContext.getApplicationContext().getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Log.e(TAG, "Error finding setting, default accessibility to not found: " + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            Log.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();
                    Log.v(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        Log.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Log.v(TAG, "***ACCESSIBILITY IS DISABLED***");
        }
        return false;
    }


    /**
     * 跳转到系统设置页面开启辅助功能
     *
     * @param accessibilityServiceName：指定辅助服务名字
     * @param context：上下文
     */
    public static void openAccessibility(String accessibilityServiceName, Context context) {
        if (!isAccessibilitySettingsOn(accessibilityServiceName, context)) {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            context.startActivity(intent);
        }
    }

    /*********************************** 使用辅助功能查找节点***************************************************/
    /*
    首先我们可以通过如下方法获取当前页面节点信息AccessibilityNodeInfo：

    AccessibilityEvent#getSource :得到AccessibilityNodeInfo事件来源
    AccessibilityService#getRootInActiveWindow :得到当前窗口根节点所有信息
    以上两个方法都可以获取到当前页面的节点信息，然后我们可以通过AccessibilityNodeInfo来查找具体的View，然后对该View操作。查找View有两如下两种方法：

    AccessibilityNodeInfo#findAccessibilityNodeInfosByText()：通过Text查找
    AccessibilityNodeInfo#findAccessibilityNodeInfosByViewId()：通过Id查找
    */

    /**
     * 根据id查找节点
     *
     * @param nodeInfo
     * @param resId
     * @return
     */
    public static AccessibilityNodeInfo findNodeInfosById(AccessibilityNodeInfo nodeInfo, String resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(resId);
            if (list != null && !list.isEmpty()) {
                return list.get(0);
            }
        }
        return null;
    }

    /**
     * 根据文本查找节点
     *
     * @param nodeInfo
     * @param text
     * @return
     */
    public static AccessibilityNodeInfo findNodeInfosByText(AccessibilityNodeInfo nodeInfo, String text) {
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /*********************************** 使用辅助功能模拟点击***************************************************/
    /*
    我们通过AccessibilityNodeInfo#findAccessibilityNodeInfosByText()查找到指定的node节点后，就可以通过相应的方法模拟点击事件了。方法如下：
    AccessibilityNodeInfo#performAction() :执行用户行为操作
    比如点击事件代码如下
    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);

    用户常用的行为事件类型一般有如下：
    ACTION_CLICK：模拟点击
    ACTION_SELECT：模拟选中
    ACTION_LONG_CLICK：模拟长按
    ACTION_SCROLL_FORWARD：模拟往前滚动
    */

}
