package com.carozhu.fastdev.update;

/**
 * #全屏BottomSheetDialogFragment
 * #设置BottomSheetDialogFragment默认的白色背景为透明。方便在你自己的布局文件中自定义背景色
 * <p>
 * #BottomSheetDialog禁止下滑关闭
 * #mBottomSheetDialogErr.setCancelable(false);
 * #mBottomSheetDialogErr.setCanceledOnTouchOutside(false);
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carozhu.fastdev.R;
import com.carozhu.fastdev.base.BaseFullScreenBottomSheetDialogFragment;
import com.carozhu.fastdev.mvp.IPresenter;
import com.carozhu.fastdev.utils.NetworkUtil;
import com.carozhu.fastdev.widget.progress.LabProgressLayout;
import com.carozhu.fastdev.widget.toast.ToastHelper;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

/**
 * Author: carozhu
 * Date  : On 2018/10/18
 * Desc  : 升级Dialog
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class UpdateDialog extends BaseFullScreenBottomSheetDialogFragment implements View.OnClickListener {
    String TAG = UpdateDialog.class.getSimpleName();
    TextView tv_not_update;
    TextView tv_update_now;
    TextView tv_update_desc;
    LabProgressLayout labProgressLayout;
    LinearLayout ll_btn_group;

    boolean isUp;
    String version;
    String remark;
    String updateUrl;

    /**
     * @param isUp 是否强制升级
     * @param version
     * @param remark
     * @param updateUrl
     * @return
     */
    public static UpdateDialog getInstance(boolean isUp,String version, String remark, String updateUrl,UpdateCallback updateCallback) {
        UpdateDialog instance = new UpdateDialog();
        instance.updateCallback = updateCallback;
        Bundle bundle = new Bundle();
        bundle.putBoolean("isUp", isUp);
        bundle.putString("version", version);
        bundle.putString("remark", remark);
        bundle.putString("updateUrl", updateUrl);
        instance.setArguments(bundle);
        return instance;
    }

    /**
     * show  Dialog
     *
     * @param manager
     * @return
     */

    public UpdateDialog show(FragmentManager manager) {
        show(manager, TAG);
        return this;
    }

    @Override
    public int getLayoutId(Dialog dialog) {
        return R.layout.dialog_update;
    }

    @Override
    public void initView(View dialogView, Dialog dialog) {
        setCancelable(false);
        Bundle bundle = getArguments();
        isUp = bundle.getBoolean("isUp");
        version = bundle.getString("version");
        remark = bundle.getString("remark");
        updateUrl = bundle.getString("updateUrl");

        tv_not_update = dialogView.findViewById(R.id.tv_not_update);
        tv_update_now = dialogView.findViewById(R.id.tv_update_now);
        tv_update_desc = dialogView.findViewById(R.id.tv_update_desc);
        labProgressLayout = dialogView.findViewById(R.id.labProgressLayout);
        ll_btn_group = dialogView.findViewById(R.id.ll_btn_group);

        tv_not_update.setOnClickListener(this);
        tv_update_now.setOnClickListener(this);
    }

    @Override
    public IPresenter initPresenter() {
        return null;
    }


    @Override
    public void render() {
        if (isUp){
            tv_not_update.setVisibility(View.VISIBLE);
        }else {
            tv_not_update.setVisibility(View.GONE);
        }
        tv_update_desc.setText(remark);

    }

    @Override
    public void recvRxEvents(Object rxPostEvent) {

    }

    @Override
    public void netReConnected(int connectType, String connectName) {

    }

    @Override
    public void netDisConnected() {

    }


    @Override
    public void onShowen() {
        if (updateCallback!=null) {
            updateCallback.onShown();
        }
    }

    @Override
    public void onHidden() {
        if (updateCallback!=null) {
            updateCallback.onDismiss();
        }
        if (fileDownloadListener != null) {
            FileDownloader.getImpl().pause(fileDownloadListener);
        }
    }


    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        if (viewID == R.id.tv_not_update){
            dismissAllowingStateLoss();
        }
        if (viewID == R.id.tv_update_now){
            if (!NetworkUtil.isNetworkAvailable(context)){
                ToastHelper.showLong(context,"网络未连接，请检查网络连接后重试");
                return;
            }

            //检测存储权限
            if (updateCallback!=null){
                updateCallback.checkStoragePermissonForDownload();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private UpdateCallback updateCallback;
    public interface UpdateCallback{
        void onShown();
        void onDismiss();
        void checkStoragePermissonForDownload();
        void downloadComplete(String downloadedfilepath);
        void downloadError(Throwable e);
    }


    private FileDownloadListener fileDownloadListener;

    /**
     * 开启下载
     * @param downloadPath_file 下载存储路径
     */
    public void startDownload(String downloadPath_file){
        ll_btn_group.setVisibility(View.GONE);
        labProgressLayout.setVisibility(View.VISIBLE);
        //开启下载
        Log.i(TAG, "开启下载downloadUrl:" + updateUrl);
        FileDownloader.getImpl().create(updateUrl)
                .setPath(downloadPath_file)
                .setListener(fileDownloadListener = new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        // Set the progress bar's progress
                        double currentProgress = (double) soFarBytes;
                        double totalProgress = (double) totalBytes;
                        int progress = (int) ((currentProgress / totalProgress) * 100);
                        labProgressLayout.setCurrentProgress(progress);

                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        String downloadedPath = task.getPath();
                        Log.i(TAG, "downloadedPath:" + downloadedPath);
                        //下载完成。进行设置
                        labProgressLayout.setCurrentProgress(100);
                        updateCallback.downloadComplete(downloadedPath);
                        startInstall(downloadedPath);
                        dismissAllowingStateLoss();
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        updateCallback.downloadError(e);

                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                }).start();
    }

    /**
     * 开启安装
     * @param downloadedFilePath
     */
    private void startInstall(String downloadedFilePath){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(getUriFromFile(context,downloadedFilePath), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }

    private static Uri getUriFromFile(Context context, String location) {
        if(Build.VERSION.SDK_INT<24){
            return   Uri.fromFile(new File(location ));
        }
        else{
            return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", new File(location));
        }
    }

}
