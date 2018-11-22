package com.carozhu.fastdev.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class RebootHelper {
    private static String TAG = RebootHelper.class.getSimpleName();

    /**
     * 重启设备
     * 需要root权限
     */
    public static void rebootDev() {
        String reboot = "adb shell" + "\n" +
                "su" + "\n" +
                "mount -o remount,rw /system" + "\n" +
                "chmod 777 /system/app/" + "\n" +
                "/system/bin/reboot" + "\n" +
                "exit" + "\n";
        if (ShellCmdHelper.execRootCmdSilent(reboot) == -1) {
            Log.i(TAG, "重启失败");
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
            System.gc();
        }
    }

    public static void restartApp(Context application) {
        Intent intent = application.getPackageManager().getLaunchIntentForPackage(application.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent restartIntent = PendingIntent.getActivity(application, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        //重启应用
        AlarmManager mgr = (AlarmManager) application.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis(), restartIntent);

        //清空Activity栈,防止系统自动重启至崩溃页面,导致崩溃再次出现.
        ActivityManageHelper.getInstance().finshAllActivities();//退出程序
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
        System.gc();
    }



}
