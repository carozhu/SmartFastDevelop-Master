package com.carozhu.fastdev.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import android.view.WindowManager;

import com.carozhu.fastdev.dialog.LoadingDialog;
import com.carozhu.fastdev.helper.DisplayHelper;
import com.carozhu.fastdev.helper.WeakHandler;
import com.carozhu.fastdev.mvp.BasePresenter;
import com.carozhu.fastdev.mvp.IContractView;
import com.carozhu.fastdev.receiver.NetChangeObser;
import com.carozhu.rxhttp.rx.RxBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: carozhu
 * Date  : On 2018/10/18
 * Desc  : 全屏且设置背景为透明的BottomSheetDialogFragment
 * 参考  : https://blog.csdn.net/klxh2009/article/details/80393245
 * <p>
 * 注意：
 * 一：BottomSheetDialogFragment的生命周期执行顺序：show-->onCreateDialog-->onCreateView
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
@SuppressLint("RestrictedApi")
public abstract class BaseFullScreenBottomSheetDialogFragment<P extends BasePresenter, V extends IContractView> extends BottomSheetDialogFragment implements IBaseDialog<P>, IContractView {
    private String TAG = BaseFullScreenBottomSheetDialogFragment.class.getSimpleName();
    protected CompositeDisposable mCompositeDisposable;
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null
    public Context context;
    public Activity activity;

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        Log.d(TAG, "setupDialog");
        View inflatedView = View.inflate(getContext(), getLayoutId(dialog), null);
        dialog.setContentView(inflatedView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) inflatedView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        View parent = (View) inflatedView.getParent();
        parent.setFitsSystemWindows(true);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(parent);
        inflatedView.measure(0, 0);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;//包含上下导航栏高度
        //减去底部导航栏高度
        screenHeight = screenHeight - DisplayHelper.getNavMenuHeight(getContext());

        bottomSheetBehavior.setPeekHeight(screenHeight);

        if (params.getBehavior() instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) params.getBehavior()).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        params.height = screenHeight;
        parent.setLayoutParams(params);
        //自定义背景为透明色
        parent.setBackgroundColor(Color.TRANSPARENT);
        context = getContext();
        activity = getActivity();
        mPresenter = initPresenter();
        initView(inflatedView, dialog);
        subscribeRxbusEvent();
        render();

        //Debug
        if (context == null) {
            Log.d(TAG, "in setupDialog context == null");
        }
        if (activity == null) {
            Log.d(TAG, "in setupDialog activity == null");
        }
    }

    /**
     * show的时候调用
     */
    @Override
    public void show(FragmentManager manager, String tag) {
        Log.d(TAG, "show");
        onShowen();
        super.show(manager, tag);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog");
        return super.onCreateDialog(savedInstanceState);
    }

    LoadingDialog loadingDialog;

    @Override
    public void showLoading(String loadingTips) {
        dismissLoading();
        loadingDialog = new LoadingDialog.Builder(context)
                .setTips(loadingTips)
                .create();
        loadingDialog.show();
    }

    @Override
    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }


    /**
     * For status Debug
     */
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_COLLAPSED:
                    Log.d(TAG, "collapsed");
                    break;
                case BottomSheetBehavior.STATE_SETTLING:
                    Log.d(TAG, "settling");
                    break;
                case BottomSheetBehavior.STATE_EXPANDED:
                    Log.d(TAG, "expanded");
                    break;
                case BottomSheetBehavior.STATE_HIDDEN:
                    Log.d(TAG, "hidden");
                    onHidden();
                    dismiss();
                    break;
                case BottomSheetBehavior.STATE_DRAGGING:
                    Log.d(TAG, "dragging");
                    break;
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            //Log.d(TAG, "sliding " + slideOffset);
        }
    };

    /**
     * Events Message Queue
     *
     * @param msg
     */
    protected void onHandleMessage(final Message msg) {

    }

    /**
     * Avoid memory leaks
     * WeakHandler
     */
    public WeakHandler weakHandler = new WeakHandler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            onHandleMessage(msg);
        }
    };

    /**
     * 订阅rxbus事件总线
     * 使用compose(this.<Long>bindUntilEvent(ActivityEvent.DESTROY))方法指定在onDestroy方法被调用时取消订阅
     * 注意compose方法需要在subscribeOn方法之后使用 @Link https://www.jianshu.com/p/7fae42861b8d
     */
    private void subscribeRxbusEvent() {
        unSubCribeRxbusEvent();
        Disposable mDisposable = RxBus.getDefault().toObservable(Object.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(object -> {
                    // do recv events
                    recvRxEvents(object);
                    if (object instanceof NetChangeObser) {
                        NetChangeObser netChangeObser = (NetChangeObser) object;
                        netChangedCallback(netChangeObser.connect, netChangeObser.connectType, netChangeObser.connectTypeName);
                    }
                }, throwable -> {
                    //ERROR 常规的Rxbus发生错误后，会取消订阅。但此时的Rxbus基于jakson的，避免了这一问题
                });
        addDispose(mDisposable);
    }

    /**
     * @解除rxbus订阅事件
     * @取消网络访问
     */
    protected void unSubCribeRxbusEvent() {
        unDispose();
    }

    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有 Disposable 放入集中处理
    }


    private void netChangedCallback(boolean connect, int connectType, String connectName) {
        if (connect) {
            netReConnected(connectType, connectName);
        } else {
            netDisConnected();
        }
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    protected void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
        }
    }

}