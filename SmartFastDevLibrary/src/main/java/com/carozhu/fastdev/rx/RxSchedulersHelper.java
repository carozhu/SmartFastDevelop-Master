package com.carozhu.fastdev.rx;

import com.carozhu.fastdev.mvp.IContractView;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.carozhu.fastdev.utils.RxLifecycleUtil.bindToLifecycle;

public class RxSchedulersHelper {

    public static <T> ObservableTransformer<T, T> io_main() {
        return upstream ->
                upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 使用RxLifebind，避免Http 内存泄漏
     * @param view
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> io_main(final IContractView view) {
        return upstream ->
                upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(bindToLifecycle(view));
    }

}
