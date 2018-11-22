package com.carozhu.fastdev.helper;

import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.carozhu.fastdev.ContextHolder;
import com.carozhu.fastdev.configure.CrashCacheConfig;
import com.carozhu.fastdev.utils.AppInfoUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 应用异常捕获类
 * 参考来着：http://www.jianshu.com/p/fb28a5322d8a
 * 修改来自：caro 2017.11.12 PM
 */

public class AppUncaughtExceptionHelper implements UncaughtExceptionHandler {
    private String TAG = AppUncaughtExceptionHelper.class.getSimpleName();
    //程序的Context对象
    private Context applicationContext;
    private String username = "";
    private volatile boolean crashing;
    private int handlelExceptionCount = 0;
    /**
     * 日期格式器
     */
    private DateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;

    /**
     * 单例
     */
    private static AppUncaughtExceptionHelper sAppUncaughtExceptionHandler;

    public static synchronized AppUncaughtExceptionHelper getInstance() {
        if (sAppUncaughtExceptionHandler == null) {
            synchronized (AppUncaughtExceptionHelper.class) {
                if (sAppUncaughtExceptionHandler == null) {
                    sAppUncaughtExceptionHandler = new AppUncaughtExceptionHelper();
                }
            }
        }
        return sAppUncaughtExceptionHandler;
    }

    /**
     * 初始化
     * @param context
     */
    public void init(Context context, String username) {
        applicationContext = context.getApplicationContext();
        crashing = false;
        if (TextUtils.isEmpty(username)){
            username = "UnKnown";
        }
        this.username = username;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 设置App用户名
     * @param username
     */
    public void setAppUsername(String username){
        if (TextUtils.isEmpty(username)){
            username = "UnKnown";
        }
        this.username = username;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (crashing) {
            return;
        }
        crashing = true;

        // 打印异常信息
        ex.printStackTrace();
        // 我们没有处理异常 并且默认异常处理不为空 则交给系统处理
        if (!handlelException(ex) && mDefaultHandler != null) {
            // 系统处理
            mDefaultHandler.uncaughtException(thread, ex);
        }
        byebye();
    }

    private void byebye() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    private boolean handlelException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        try {
            // 缓存重启次数到sp方便判断 。 次数太多网络良好则上报
            // 保存重新启动次数。如次数重启启动太多，则kill掉
            if (handlelExceptionCount > 5) {
                Log.i("crash", "handlelExceptionCount=" + handlelExceptionCount);
            }
            handlelExceptionCount++;
            // 异常信息
            String crashReport = getCrashReport(ex);
            // TODO: 上传日志到服务器
            EmailReportHelper.doEmialReport("异常崩溃日志:", crashReport);
            // 保存到sd卡
            saveExceptionToSdcard(crashReport);
            //异常后重启
            SystemClock.sleep(1000);
            RebootHelper.restartApp(ContextHolder.getContext());

        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * 获取异常信息
     *
     * @param ex
     * @return
     */
    public String getCrashReport(Throwable ex) {
        StringBuffer exceptionStr = new StringBuffer();
        if (ex != null) {
            //app版本信息
            exceptionStr.append("App AppName：" + AppInfoUtil.getAppName(applicationContext) + "\n");
            exceptionStr.append("App VerName：" + AppInfoUtil.getVerName(applicationContext) + "\n");
            exceptionStr.append("App VerCode：" + AppInfoUtil.getVerCode(applicationContext) + "\n");
            exceptionStr.append("App 用 户 名：" + username + "\n");
            exceptionStr.append("App CrashTime：" + parserTime(System.currentTimeMillis()) + "\n");

            //手机系统信息
            exceptionStr.append("OS Version：" + Build.VERSION.RELEASE);
            exceptionStr.append("_");
            exceptionStr.append(Build.VERSION.SDK_INT + "\n");
            //手机制造商
            exceptionStr.append("Vendor: " + Build.MANUFACTURER + "\n");
            //手机型号
            exceptionStr.append("Model: " + Build.MODEL + "\n");


            String errorStr = ex.getLocalizedMessage();
            if (TextUtils.isEmpty(errorStr)) {
                errorStr = ex.getMessage();
            }
            if (TextUtils.isEmpty(errorStr)) {
                errorStr = ex.toString();
            }
            exceptionStr.append("Exception: " + errorStr + "\n");
            StackTraceElement[] elements = ex.getStackTrace();
            if (elements != null) {
                for (int i = 0; i < elements.length; i++) {
                    exceptionStr.append(elements[i].toString() + "\n");
                }
            }
        } else {
            exceptionStr.append("no exception. Throwable is null\n");
        }
        return exceptionStr.toString();

    }

    /**
     * 将毫秒数转换成yyyy-MM-dd-HH-mm-ss的格式，并在后缀加入随机数
     *
     * @param milliseconds
     * @return
     */
    private String parserTime(long milliseconds) {
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return format.format(new Date(milliseconds));
    }

    /**
     * 保存错误报告到sd卡
     *
     * @param errorReason
     */
    public void saveExceptionToSdcard(final String errorReason) {
        try {
            Log.e("CrashDemo", "AppUncaughtExceptionHandler执行了一次");
            String time = mFormatter.format(new Date());
            String fileName = "Crash-" + time + ".log";
            if (CrashCacheConfig.getInstance().hasSDCard()) {
                String path = CrashCacheConfig.getInstance().getLogFolder();
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(errorReason.getBytes());
                fos.close();
            }
        } catch (Exception e) {
            Log.e("CrashDemo", "an error occured while writing file..." + e.getMessage());
        }
    }


}
