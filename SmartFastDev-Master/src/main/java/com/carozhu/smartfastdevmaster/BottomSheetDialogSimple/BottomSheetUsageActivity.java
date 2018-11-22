package com.carozhu.smartfastdevmaster.BottomSheetDialogSimple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.carozhu.fastdev.base.BaseActivity;
import com.carozhu.fastdev.mvp.BasePresenter;
import com.carozhu.fastdev.mvp.IPresenter;
import com.carozhu.smartfastdevmaster.R;

import butterknife.OnClick;

/**
 * @author caro
 */
public class BottomSheetUsageActivity extends BaseActivity {


    @Override
    public int getLayoutId(@Nullable Bundle savedInstanceState) {
        return R.layout.bottomsheet_uage_guide_activity;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }


    @Override
    public void render() {

//        /**       BottomSheetDialog dialog 使用 */
//        StatusbarBottomSheetDialog dialog = new StatusbarBottomSheetDialog(context);
//         View box_view = LayoutInflater.from(context).inflate(R.layout.activity_comitcheckin_desc, null);
//         dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//         dialog.setContentView(box_view);
//         dialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
//         .setBackgroundColor(context.getResources().getColor(R.color.colorTranslate));
//
//         dialog.setCancelable(true);
//         dialog.setCanceledOnTouchOutside(true);
//         dialog.show();
//
//
//
//        FrameLayout bottomSheet = (FrameLayout) dialog.getDelegate().findViewById(R.id.design_bottom_sheet);
//        assert bottomSheet != null;
//        BottomSheetBehavior<FrameLayout> mBehavior = BottomSheetBehavior.from(bottomSheet);
//        mBehavior.setPeekHeight(120);
//        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

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

    @OnClick(R.id.default_useage)
    public void defaultUseage(){
        MyStrongBottomSheetDialog myStrongBottomSheetDialog =   new MyStrongBottomSheetDialog(context);
        myStrongBottomSheetDialog.setMaxHeight(800);
        myStrongBottomSheetDialog.show();
    }


}
