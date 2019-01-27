package com.carozhu.fastdev.base;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.carozhu.fastdev.R;
import com.carozhu.fastdev.mvp.BasePresenter;
import com.carozhu.fastdev.mvp.IContractView;
import com.carozhu.fastdev.widget.multview.EmptyErrorMultView;
import com.carozhu.fastdev.widget.multview.LoadingMSVView;
import com.carozhu.fastdev.widget.multview.MultViewEventsListener;
import com.carozhu.fastdev.widget.multview.MultiStateView;
import com.carozhu.fastdev.widget.rv.LoadMoreDelegate;
import com.carozhu.fastdev.widget.rv.SwipeRefreshDelegate;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: carozhu
 * Date  : On 2018/12/13
 * Desc  : RfLdmMultRv  --  refresh loadmore multView recyclerView
 */
public abstract class BaseRfLdmMultRvActivity<P extends BasePresenter, V extends IContractView, RV extends RecyclerView, swipeRefreshLayout extends SwipeRefreshLayout, multiStateView extends MultiStateView>
        extends BaseActivity<P, V>
        implements SwipeRefreshDelegate.OnSwipeRefreshListener, LoadMoreDelegate.LoadMoreSubject {

    private String TAG = BaseRfLdmMultRvActivity.class.getSimpleName();
    protected RV recyclerView;
    protected swipeRefreshLayout swipeRefreshLayout;
    protected multiStateView multiStateView;

    protected MultiTypeAdapter adapter;
    protected Items items;

    protected SwipeRefreshDelegate refreshDelegate;
    protected LoadMoreDelegate loadMoreDelegate;
    protected AtomicInteger loadingCount;
    protected boolean isEnd;
    protected boolean isLoading = false;

    protected LoadingMSVView loadingMSVView;
    protected EmptyErrorMultView emptyMultView;
    protected EmptyErrorMultView errorMultView;

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        recyclerView = registerRecyclerView();
        recyclerView.setLayoutManager(registerLayoutManager());
        if (recyclerView != null) {
            items = new Items();
            adapter = new MultiTypeAdapter(items);
            recyclerView.setAdapter(adapter);
            loadMoreDelegate = new LoadMoreDelegate(this);
            loadMoreDelegate.attach(recyclerView);
            loadingCount = new AtomicInteger(0);
        }

        swipeRefreshLayout = registerSwipeRefreshLayout();
        if (swipeRefreshLayout != null) {
            refreshDelegate = new SwipeRefreshDelegate(this);
            refreshDelegate.attach(activity, swipeRefreshLayout);
            //you can custom
            refreshDelegate.setRefreshSchemeColors(
                    ContextCompat.getColor(context, R.color.md_deep_orange_500),
                    ContextCompat.getColor(context, R.color.md_deep_orange_500),
                    ContextCompat.getColor(context, R.color.md_deep_orange_500),
                    ContextCompat.getColor(context, R.color.md_deep_orange_500));
        }
        multiStateView = registerMultiStateView();
        loadingMSVView =(LoadingMSVView) multiStateView.getView(MultiStateView.VIEW_STATE_LOADING);
        emptyMultView =(EmptyErrorMultView) multiStateView.getView(MultiStateView.VIEW_STATE_EMPTY);
        errorMultView =(EmptyErrorMultView) multiStateView.getView(MultiStateView.VIEW_STATE_ERROR);
        setMultLoading();
    }

    @Override
    public void render() {
        notifyLoadingStarted();
        loadData(true, getLoadingIndex());
    }


    protected abstract RV registerRecyclerView();

    protected abstract RecyclerView.LayoutManager registerLayoutManager();

    protected abstract swipeRefreshLayout registerSwipeRefreshLayout();

    protected abstract multiStateView registerMultiStateView();

    protected abstract void loadData(boolean clear, int loadPage);


    @Override
    public void onSwipeRefresh() {
        notifyLoadingStarted();
        loadData(true, getLoadingIndex());
    }


    @Override
    public final void onLoadMore() {
        if (!isEnd()) {
            Log.d(TAG, "[onLoadMore]" + "isEnd == false");
            if (!onInterceptLoadMore()) {
                notifyLoadingStarted();
                loadData(false, getLoadingIndex());
            }
        }
    }


    /**
     * 设置是否刷新
     *
     * @param refresh
     */
    protected void setRefresh(boolean refresh) {
        refreshDelegate.setRefresh(refresh);
    }

    protected boolean isShowingRefresh() {
        return refreshDelegate.isShowingRefresh();
    }

    protected void setSwipeToRefreshEnabled(boolean enable) {
        refreshDelegate.setEnabled(enable);
    }

    protected void setLoadmoreEnd(boolean end) {
        isEnd = end;
    }


    protected boolean isEnd() {
        return isEnd;
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }


    private void notifyLoadingStarted() {
        loadingCount.getAndIncrement();//++
        int loadindex = loadingCount.get();
        Log.i(TAG, "notifyLoadingStarted -- loadindex: " + loadindex);
        isLoading = true;
        if (items != null && items.size() == 0) {
            setMultLoading();
        }
    }

    /**
     * 使当前页面Loadpage ++
     */
    public void incrementLoadPage(){
        loadingCount.getAndIncrement();//++
    }

    /**
     * 设置当前Load page
     * @param loadPage
     */
    public void setLoadPage(int loadPage){
        loadingCount.set(loadPage);
    }

    protected void notifyLoadingFinished() {
        isLoading = false;
        int loadindex = loadingCount.get();
        Log.i(TAG, "notifyLoadingFinished -- loadindex: " + loadindex);
        if (items != null) {
            if (items.size() > 0) {
                setMultContent();
            } else {
                setMultEmpty();
            }

        }
    }

    /**
     * decrementAndGet
     * 加载失败时，将当前的load index -- 回到之前的load page
     */
    protected void notifyLoadingFailed() {
        isLoading = false;
        loadingCount.decrementAndGet();// --
        int loadindex = loadingCount.get();
        Log.i(TAG, "notifyLoadingFinished -- loadindex: " + loadindex);
        if (items != null && items.size() == 0) {
            setMultError();
        }else {
            setMultContent();
        }
    }

    protected boolean onInterceptLoadMore() {
        return false;
    }

    public void smoothScrollToPosition(RecyclerView recyclerView, int position) {
        recyclerView.smoothScrollToPosition(position);
    }

    protected int getLoadingIndex() {
        int loadindex = loadingCount.get();
        Log.i(TAG, "getLoadingIndex -- loadindex: " + loadindex);
        return loadindex;
    }

    protected void initLoadingdex() {
        loadingCount.intValue();
        int loadindex = loadingCount.get();
        Log.i(TAG, "initLoadingdex -- loadindex: " + loadindex);
    }

    /**
     * 设置Loading视图状态
     */
    protected void setMultLoading() {
        if (multiStateView != null && multiStateView.getViewState() != MultiStateView.VIEW_STATE_LOADING) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        }
    }

    /**
     * 设置数据视图状态
     */
    protected void setMultContent() {
        if (multiStateView != null && multiStateView.getViewState() != MultiStateView.VIEW_STATE_CONTENT) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
    }

    /**
     * 设置错误视频状态
     */
    protected void setMultError() {
        if (multiStateView != null && multiStateView.getViewState() != MultiStateView.VIEW_STATE_ERROR) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    /**
     * 设置为空视图状态
     */
    protected void setMultEmpty() {
        if (multiStateView != null && multiStateView.getViewState() != MultiStateView.VIEW_STATE_EMPTY) {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }


    /**
     * 绑定空视图状态时的数据
     * @param resId
     * @param action_name
     * @param action_text_color
     * @param tips
     * @param tipsImage
     */
    protected void bindEmptyTips(@DrawableRes int resId, String action_name, int action_text_color, String tips, int tipsImage, MultViewEventsListener multViewEventsListener){
        emptyMultView
                .setActionBackground(resId)
                .setActionText(action_name)
                .setActionTextColor(action_text_color)
                .setTipText(tips)
                .setTipsImage(tipsImage)
                .setMultViewEventsListener(multViewEventsListener);
    }


    /**
     * 绑定空视图状态时的数据
     * @param resId
     * @param action_name
     * @param action_text_color
     * @param tips
     * @param tipsImage
     */
    protected void bindErrorTips(@DrawableRes int resId,String action_name,int action_text_color,String tips,int tipsImage,MultViewEventsListener multViewEventsListener){
        errorMultView
                .setActionBackground(resId)
                .setActionText(action_name)
                .setActionTextColor(action_text_color)
                .setTipText(tips)
                .setTipsImage(tipsImage)
                .setMultViewEventsListener(multViewEventsListener);
    }


    /**
     * 绑定Loading视图
     * @param loadingTips
     * @param tipTextColor
     * @param loadingBackgroundColor
     * @param loadingBackgroundImgResID
     */
    protected void bindLoadingTips(String loadingTips, @ColorInt int tipTextColor, @ColorInt int loadingBackgroundColor, @DrawableRes int loadingBackgroundImgResID){
        loadingMSVView
                .setLoadingBackgroundColor(loadingBackgroundColor)
                .setLoadingBackgroundImageResource(loadingBackgroundImgResID)
                .setTipText(loadingTips)
                .setTipTextColor(tipTextColor);
    }
}
