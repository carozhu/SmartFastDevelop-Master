package com.carozhu.fastdev.mvp;

public interface IContractView<T> {
    /**
     * show Loading
     */
    void showLoading(String loadingTips);

    /**
     * dismiss Loading
     */
    void dismissLoading();
}
