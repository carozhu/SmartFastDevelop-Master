package com.carozhu.fastdev.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 默认值
 * corePoolSize=1
 * queueCapacity=Integer.MAX_VALUE
 * maxPoolSize=Integer.MAX_VALUE
 * keepAliveTime=60s
 * allowCoreThreadTimeout=false
 * rejectedExecutionHandler=AbortPolicy()
 */
public interface ICachedThreadPool {
    /*
     * 核心线程数量
     */
    static final int THREADS_CORE_COUNT = Runtime.getRuntime().availableProcessors();
    //static final int THREADS_CORE_COUNT = 16;
    //static final int THREADS_MAX_COUNT = 32;
    static final int THREADS_MAX_COUNT = Integer.MAX_VALUE;
    static final long THREADS_KEEP_ALIVE_TIME = 60L;//default
    static final TimeUnit THREADS_KEEP_ALIVE_UNIT = TimeUnit.SECONDS;

    /**
     * @param task
     * @return
     * @see ExecutorService
     */
    <T> Future<T> submit(final Callable<T> task);

    /**
     * @param task
     * @return
     * @see ExecutorService
     */
    Future<?> submit(final Runnable task);

    /**
     * @param task       the task to be computed
     * @param taskSuc    to do task if task suc
     * @param taskFail   to do task if task fail
     * @param taskCancel to do task if task is cancelled
     * @return @see Future
     */
    <T> Future<T> submit(final Callable<T> task, final Runnable taskSuc, final Runnable taskFail,
                         final Runnable taskCancel);

    /**
     * @param task       the task to be executed
     * @param taskSuc    to do task if task suc
     * @param taskFail   to do task if task fail
     * @param taskCancel to do task if task is cancelled
     * @return T executed result
     */
    <T> T execute(final Callable<T> task, final Runnable taskSuc, final Runnable taskFail, final Runnable taskCancel);

    /**
     * cancel all of the task in the threadpool
     */
    void cancelAllTask();

    /**
     * @see ExecutorService
     */
    void shutdown();

    /**
     * @see ExecutorService
     */
    void shutdownNow();
}