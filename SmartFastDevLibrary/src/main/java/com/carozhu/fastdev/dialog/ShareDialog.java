package com.carozhu.fastdev.dialog;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.carozhu.fastdev.R;
import com.carozhu.fastdev.base.BaseFullScreenBottomSheetDialogFragment;
import com.carozhu.fastdev.mvp.IContractView;

/**
 * Author: carozhu
 * Date  : On 2018/12/14
 * Desc  : simple share dialog
 */
public class ShareDialog extends BaseFullScreenBottomSheetDialogFragment implements IContractView,View.OnClickListener {
    Button cancelBtn;
    TextView tv_share_qq_friend;
    TextView tv_share_qq_zone;
    TextView tv_share_weixin_friend;
    TextView tv_share_weixin_zone;


    @Override
    public int getLayoutId(Dialog dialog) {
        return R.layout.dialog_share;
    }

    @Override
    public void initView(View dialogView, Dialog dialog) {
        cancelBtn = dialogView.findViewById(R.id.cancelBtn);
        tv_share_qq_friend = dialogView.findViewById(R.id.tv_share_qq_friend);
        tv_share_qq_zone = dialogView.findViewById(R.id.tv_share_qq_zone);
        tv_share_weixin_friend = dialogView.findViewById(R.id.tv_share_weixin_friend);
        tv_share_weixin_zone = dialogView.findViewById(R.id.tv_share_weixin_zone);


        tv_share_qq_friend.setOnClickListener(this);
        tv_share_qq_zone.setOnClickListener(this);
        tv_share_weixin_friend.setOnClickListener(this);
        tv_share_weixin_zone.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

    }

    @Override
    public Object initPresenter() {
        return null;
    }

    @Override
    public void render() {

    }

    @Override
    public void onShowen() {

    }

    @Override
    public void onHidden() {

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

    LoadingDialog loadingDialog;

    @Override
    public void showLoading(String loadingTips){
        dismissLoading();
        loadingDialog = new LoadingDialog.Builder(getContext())
                .setTips(loadingTips)
                .create();
        loadingDialog.show();

    }

    @Override
    public void dismissLoading(){
        if (loadingDialog!=null && loadingDialog.isShowing()){
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    @Override
    public void onClick(View v) {

    }
}
