package com.zsyj.videocall.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;


public class AccessibilityServiceMonitor extends AccessibilityService {

    private static final String TAG = AccessibilityServiceMonitor.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null == intent) {
            return super.onStartCommand(intent, flags, startId);
        }

        String action = intent.getAction();
        Log.d(TAG, "onStartCommand Aciton: " + action);

        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo serviceInfo = new AccessibilityServiceInfo();
        serviceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        serviceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        serviceInfo.packageNames = new String[]{"com.android.packageinstaller","com.android.settings"};// 监控的app
        serviceInfo.notificationTimeout = 100;
        serviceInfo.flags = serviceInfo.flags | AccessibilityServiceInfo.FLAG_REQUEST_ACCESSIBILITY_BUTTON;
        setServiceInfo(serviceInfo);
    }
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        String packageName = event.getPackageName().toString();
        String className = event.getClassName().toString();
        Log.d(TAG,"packageName = " + packageName + ", className = " + className);

        AccessibilityNodeInfo nodeInfo  = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list  = nodeInfo.findAccessibilityNodeInfosByText("始终允许");
            Log.i(TAG,"FIND  list.size ： "+list.size());

            for (int i = 0; i < nodeInfo.getChildCount(); i++) {
                AccessibilityNodeInfo child =  nodeInfo.getChild(i);
                if (child!=null){
                    Log.d(TAG, " count = " + child.getChildCount());
                }


            }
        } else {
            Log.d(TAG, "alipayPolicy = null");
        }

        AccessibilityNodeInfo souce = event.getSource();
        if (souce!=null) {
            List<AccessibilityNodeInfo> list = souce.findAccessibilityNodeInfosByText("始终允许");
            if (null != list && list.size() > 0) {
                Log.i(TAG, "FIND  始终允许");
                for (AccessibilityNodeInfo info : list) {
                    if (info.getText().toString().equals("始终允许")) {
                        //找到你的节点以后 就直接点击他就行了
                        Log.i(TAG, "FIND  始终允许 点击啦啦啦");
                        info.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                        info.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                    }
                }
            }
        }


        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:

                Log.i(TAG,"eventType case   TYPE_WINDOW_STATE_CHANGED && TYPE_VIEW_SCROLLED");
                break;

        }
    }


    @Override
    public void onInterrupt() {

    }




}
