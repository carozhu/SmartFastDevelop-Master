package com.carozhu.fastdev.helper;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ShellCmdHelper {
    private static String TAG = ShellCmdHelper.class.getSimpleName();
    /**
     * 执行linux命令
     *
     * @param paramString
     * @return 0：正常结束，其他：返回子进程数subProcess
     */
    public static int execRootCmdSilent(String paramString) {
        Process localProcess = null;
        try {
            localProcess = Runtime.getRuntime().exec("su");//"/system/xbin/su");
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        Object localObject = localProcess.getOutputStream();
        DataOutputStream localDataOutputStream = new DataOutputStream((OutputStream) localObject);
        String str = String.valueOf(paramString);
        localObject = str + "\n";
        Log.i(TAG, str);
        try {
            localDataOutputStream.writeBytes((String) localObject);
            localDataOutputStream.flush();
            localDataOutputStream.writeBytes("exit\n");
            localDataOutputStream.flush();
            Log.i(TAG, localDataOutputStream.toString());
            try {
                localProcess.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return -1;
            }
            Log.i(TAG, "localProcess.exitValue() === " + localProcess.exitValue());
            int result = localProcess.exitValue();
            return (Integer) result;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
