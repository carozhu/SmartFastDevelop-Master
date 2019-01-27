package com.carozhu.fastdev.glide;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;
import com.carozhu.fastdev.glide.progress.GlideApp;

public class GlideHelper {

    public static void load(Context mContext, String imageUrl, @DrawableRes int plachHolder, @DrawableRes int errorHolder, ImageView imageView) {
        GlideApp.with(mContext)
                .load(imageUrl)
                .placeholder(plachHolder)
                .error(errorHolder)
                .into(imageView);
    }


    public static void load(Context mContext, String imageUrl, ImageView imageView) {
        GlideApp.with(mContext)
                .load(imageUrl)
                .into(imageView);
    }


    public static void loadIcon(Context mContext, String imageUrl, ImageView imageView) {
        GlideApp.with(mContext)
                .load(imageUrl)
                .into(imageView);
    }

    public static void load(Context mContext, int imagreResId, ImageView imageView) {
        GlideApp.with(mContext)
                .load(imagreResId)
                .into(imageView);
    }

    /**
     *
     * @param mContext
     * @param imageUrl
     * @param imageView
     * @param compress 如果你的图片存储在阿里云CDN上。可以尝试添加以下后缀
     */
    public static void load(Context mContext, String imageUrl, ImageView imageView, boolean compress) {
        if (compress) {
            imageUrl = imageUrl + "?x-oss-process=image/format,jpg/quality,q_90";
        }
        GlideApp.with(mContext)
                .load(imageUrl)
                .into(imageView);
    }


    /**
     *
     * @param mContext
     * @param imageUrl
     * @param imageView
     * @param compress 如果你的图片存储在阿里云CDN上。可以尝试添加以下后缀
     */
    public static void load(Context mContext, String imageUrl, ImageView imageView, boolean compress,@DrawableRes int plachHolder, @DrawableRes int errorHolder) {
        if (compress) {
            imageUrl = imageUrl + "?x-oss-process=image/format,jpg/quality,q_90";
        }
        GlideApp.with(mContext)
                .load(imageUrl)
                .placeholder(plachHolder)
                .error(errorHolder)
                .into(imageView);
    }
}
