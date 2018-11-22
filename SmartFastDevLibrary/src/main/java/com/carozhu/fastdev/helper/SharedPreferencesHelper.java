package com.carozhu.fastdev.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.carozhu.fastdev.utils.AppInfoUtil;


public class SharedPreferencesHelper {
    /**
     * save stringFiled refre field to sp
     * <p>
     * 当前仅用来保存sid
     *
     * @param mContext
     * @param field
     * @param value
     */
    public static void savefieldtoSharePre(Context mContext, String field, String value) {
        SharedPreferences sp = (SharedPreferences) mContext.getSharedPreferences(AppInfoUtil.getAppName(mContext), 0);
        sp.edit().putString(field, value).commit();
    }


    /**
     * get stringFiled from sp
     *
     * @param mContext
     * @return
     */
    public static String getValuewithField(Context mContext, String filed) {
        SharedPreferences sp = (SharedPreferences) mContext.getSharedPreferences(AppInfoUtil.getAppName(mContext), 0);
        String stringFiled = sp.getString(filed, "");
        return stringFiled;
    }


    /**
     * save stringFiled refre field to sp
     * <p>
     * 当前仅用来保存sid
     *
     * @param mContext
     * @param field
     * @param value
     */
    public static void savefieldtoSharePre(Context mContext, String field, boolean value) {
        SharedPreferences sp = (SharedPreferences) mContext.getSharedPreferences(AppInfoUtil.getAppName(mContext), 0);
        sp.edit().putBoolean(field, value).commit();
    }


    /**
     * get stringFiled from sp
     *
     * @param mContext
     * @return
     */
    public static boolean getBooleanValuewithField(Context mContext, String filed) {
        SharedPreferences sp = (SharedPreferences) mContext.getSharedPreferences(AppInfoUtil.getAppName(mContext), 0);
        Boolean stringFiled = sp.getBoolean(filed, false);
        return stringFiled;
    }

    public static void saveLongfieldtoSharePre(Context mContext, String field, long value) {
        SharedPreferences sp = (SharedPreferences) mContext.getSharedPreferences(AppInfoUtil.getAppName(mContext), 0);
        sp.edit().putLong(field, value).commit();
    }


    /**
     * get stringFiled from sp
     *
     * @param mContext
     * @return
     */
    public static long getLongValuewithField(Context mContext, String filed) {
        SharedPreferences sp = (SharedPreferences) mContext.getSharedPreferences(AppInfoUtil.getAppName(mContext), 0);
        long longFiled = sp.getLong(filed, 0);
        return longFiled;
    }


    public static void saveIntfieldtoSharePre(Context mContext, String field, int value) {
        SharedPreferences sp = (SharedPreferences) mContext.getSharedPreferences(AppInfoUtil.getAppName(mContext), 0);
        sp.edit().putInt(field, value).commit();
    }


    /**
     * get stringFiled from sp
     *
     * @param mContext
     * @return
     */
    public static int getIntValuewithField(Context mContext, String filed) {
        SharedPreferences sp = (SharedPreferences) mContext.getSharedPreferences(AppInfoUtil.getAppName(mContext), 0);
        int longFiled = sp.getInt(filed, 0);
        return longFiled;
    }

    public static void cleanSpFileld(Context mContext,String filed){
        SharedPreferences sp = (SharedPreferences) mContext.getSharedPreferences(AppInfoUtil.getAppName(mContext), 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(filed);
        editor.commit();
    }

}
