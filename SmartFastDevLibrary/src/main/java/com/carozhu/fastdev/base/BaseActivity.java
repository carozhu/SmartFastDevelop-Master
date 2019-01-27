package com.carozhu.fastdev.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import android.view.WindowManager;
import com.carozhu.fastdev.dialog.LoadingDialog;
import com.carozhu.fastdev.helper.ActivityManageHelper;
import com.carozhu.fastdev.helper.StatusBarHelper;
import com.carozhu.fastdev.helper.WeakHandler;
import com.carozhu.fastdev.lifecycle.ActivityLifecycleable;
import com.carozhu.fastdev.mvp.BasePresenter;
import com.carozhu.fastdev.mvp.IContractView;
import com.carozhu.fastdev.receiver.NetChangeObser;
import com.carozhu.rxhttp.rx.RxBus;
import com.trello.rxlifecycle2.android.ActivityEvent;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;


import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * weakHandler使用注意事项
 * 1：删除所有消息和回调 handler.removeCallbacksAndMessages(null);
 * 2：删除在消息队列中的任何待处理的Runnable handler.removeCallbacks(runnableRunner);
 */
public abstract class BaseActivity<P extends BasePresenter, V extends IContractView> extends RxAppCompatActivity implements ActivityLifecycleable, SwipeBackActivityBase, IBaseActivity<P> ,IContractView{
    // 右滑返回
    private SwipeBackLayout mSwipeBackLayout;
    private SwipeBackActivityHelper mHelper;
    public Activity activity;
    public Context context;
    private Unbinder mUnbinder;
    protected CompositeDisposable mCompositeDisposable;
    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();

    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null

    private LoadingDialog loadingDialog = null;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContent();
        setContentView(getLayoutId(savedInstanceState));
        activity = this;
        context = this;
        /*must init helper*/
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mUnbinder = ButterKnife.bind(this);
        ActivityManageHelper.getInstance().addActivity(this);

        mPresenter = initPresenter();
        initView(savedInstanceState);
        render();
        subscribeRxbusEvent();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    /**
     * @param swipeBack
     */
    public void swipeBack(boolean swipeBack) {
        setSwipeBackEnable(swipeBack);
    }

    /**
     * @param swipeBack
     * @param edgeFlags 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
     */
    public void swipeBack(boolean swipeBack, int edgeFlags) {
        mSwipeBackLayout.setEdgeTrackingEnabled(edgeFlags);
        setSwipeBackEnable(swipeBack);
    }


    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }


    @NonNull
    @Override
    public final Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    /**
     * 默认禁用横屏。
     * 不需要时，请不要super
     */
    protected void beforeSetContent() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) mPresenter.attachView((V) this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public <T extends View> T findViewById(@IdRes int id) {
        return super.findViewById(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManageHelper.getInstance().finshActivity(this);
        unSubCribeRxbusEvent();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
        this.mUnbinder = null;
        if (mPresenter != null) {
            mPresenter.onDestroy();//释放资源
            mPresenter.detachView();
            this.mPresenter = null;
        }
        //移除消息队列中所有消息和所有的Runnable
        if (weakHandler != null) {
            weakHandler.removeCallbacksAndMessages(null);
            weakHandler = null;
        }
    }


    @Override
    public void onBackPressed() {
        onSwitchBackPressed();
    }


    private void netChangedCallback(boolean connect, int connectType, String connectName) {
        if (connect) {
            netReConnected(connectType, connectName);
        } else {
            netDisConnected();
        }
    }


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
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
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

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    protected void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
        }
    }


    /**
     * if you will handle back key,plsease override this fuction
     */
    protected void onSwitchBackPressed() {
        finish();
    }

    /**
     * root view
     *
     * @return
     */
    public View getRootView() {
        return activity.getWindow().getDecorView().findViewById(android.R.id.content);
    }


    // ------------   Dialog start ----------------

    /**
     * 显示正在Loading Dialog
     *
     * @param loadingTips
     */
    @Override
    public void showLoading(String loadingTips) {
        loadingDialog = new LoadingDialog.Builder(context)
                .setTips(loadingTips)
                .create();
        loadingDialog.show();
    }

    /**
     * dis SmileLoading Dialog
     */
    @Override
    public void dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    /**
     * @param statuTips
     */
    public void showStatuTips(String statuTips) {
        if (loadingDialog != null && !TextUtils.isEmpty(statuTips)) {
            loadingDialog.setStatuTip(statuTips);
        }
    }

    /**
     * @return
     */
    public LoadingDialog getLoadingDialog() {
        if (loadingDialog == null) {
            throw new RuntimeException("you yet show smileLoading Dialog ! ");
        }
        return loadingDialog;
    }
    // ------------   Dialog end  ----------------


    // ------------ 状态栏 start ------------------------
    public void setTransparentForWindow(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @param statuBarColor  状态栏颜色
     * @param statusBarAlpha 状态栏颜色透明度
     * @param useDart        图标文字是否使用深色调
     */
    protected void setStatuBarStyle(@ColorRes int statuBarColor, @IntRange(from = 0, to = 255) int statusBarAlpha, boolean useDart) {
        StatusBarHelper.setColor(this, ContextCompat.getColor(this, statuBarColor), statusBarAlpha);
        StatusBarHelper.setStatusTextColor(useDart, this);
    }
    // ---------- 状态栏 end ----------------------------


    // ---------- 跳转 start ----------------------------

    /**
     * just jump a sample activity
     *
     * @param context
     * @param distClass
     */
    public void startActivity(Context context, Class<?> distClass) {
        Intent in = new Intent(context, distClass);
        startActivity(in);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public static void startActivity(Context mContext, Class<?> toCls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, toCls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
    }
    // ---------- 跳转 end ----------------------------


}
