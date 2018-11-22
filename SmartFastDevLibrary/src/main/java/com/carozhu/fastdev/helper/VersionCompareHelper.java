package com.carozhu.fastdev.helper;

/**
 * Created by caro on 16/3/21.
 * updaate dialog helper
 * you can custom your upgrade title and content
 * sample usage:
 * TODO: 16/3/22 you  need compare serversion and localversion
 * try {
 * if (VersionCompareHelper.compareVersion(serVersion, localAppVer) > 0) {
 * VersionCompareHelper.Update(context, dwnApkUrl, serVersion, intr);
 * }else {
 * TODO: 16/3/29 lastest
 * <p>
 * }
 * } catch (Exception e) {
 * e.printStackTrace();
 * }
 */
public class VersionCompareHelper {

    /**
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
     *
     * @param serVersion  local verser
     * @param localAppVer localAppVer
     * @return
     */
    public static int compareVersion(String serVersion, String localAppVer) {
        if (serVersion == null || localAppVer == null) {
            return -1;
        }
        String[] versionArray1 = serVersion.split("\\.");//注意此处为正则匹配，不能用"."；
        String[] versionArray2 = localAppVer.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }
}
