package com.carozhu.fastdev.glide.progress;

/**
 * @author by sunfusheng on 2017/6/14.
 */
public interface OnProgressListener {
    void onProgress(boolean isComplete, int percentage, long bytesRead, long totalBytes);
}
