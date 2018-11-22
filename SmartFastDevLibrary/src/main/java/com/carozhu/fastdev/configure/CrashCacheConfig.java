package com.carozhu.fastdev.configure;

import android.os.Environment;

import com.carozhu.fastdev.ContextHolder;
import com.carozhu.fastdev.utils.AppInfoUtil;

import java.io.File;


public class CrashCacheConfig {

	/**
	 * sdcard
	 */
	private  final String SDCARD_FOLDER = Environment.getExternalStorageDirectory().getPath();

	/**
	 * 根目录
	 * default
	 */
	private   String ROOT_FOLDER = SDCARD_FOLDER + "/crash/";

	/**
	 * 日志目录
	 * default
	 */
	private   String LOG_FOLDER = ROOT_FOLDER + "log/";

	private static CrashCacheConfig crashCacheConfig;

	public CrashCacheConfig(){
		ROOT_FOLDER = SDCARD_FOLDER + "/"+ AppInfoUtil.getAppName(ContextHolder.getContext())+"_Crash";
		LOG_FOLDER = ROOT_FOLDER + "log/";
	}

	public static synchronized CrashCacheConfig getInstance() {
		if (crashCacheConfig == null) {
			crashCacheConfig = new CrashCacheConfig();
		}
		return crashCacheConfig;
	}

	public String getLogFolder(){
		return LOG_FOLDER;
	}

	/**
	 * sd卡初始化
	 */
	public void initSdcard() {
		if (!hasSDCard())
			return;
		File logFile = new File(LOG_FOLDER);
		if (!logFile.exists()) {
			logFile.mkdirs();
		}
	}

	/**
	 * 判断是否存在SDCard
	 *
	 * @return
	 */
	public boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		return status.equals(Environment.MEDIA_MOUNTED);
	}

}
