package com.carozhu.fastdev.priorityThreadPool;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
/**
 * 默认工作线程数5
 * 默认值
 * corePoolSize=1
 * queueCapacity=Integer.MAX_VALUE
 * maxPoolSize=Integer.MAX_VALUE
 * keepAliveTime=60s
 * allowCoreThreadTimeout=false
 * rejectedExecutionHandler=AbortPolicy()
 */

/**
 * 带优先级的线程池
 */
public class PriorityThreadPoolExecutor extends ThreadPoolExecutor {
    /*
     * 核心线程数量
     */
    private static final int THREADS_CORE_COUNT = Runtime.getRuntime().availableProcessors();//核心线程池大小
    private static final int THREADS_MAX_COUNT = 256;//最大线程池队列大小
    private static final long THREADS_KEEP_ALIVE_TIME = 60L;//default //保持存活时间，当线程数大于corePoolSize的空闲线程能保持的最大时间。
    private static final TimeUnit THREADS_KEEP_ALIVE_UNIT = TimeUnit.SECONDS;
    private static final AtomicLong SEQ_SEED = new AtomicLong(0);//主要获取添加任务
    private static boolean fifo = false; //default -- 加入的ruannabe线程，优先级越高的就越先执行

    /**
     * please init configure in your application
     * @param blnfifo
     */
    public static void initConfigPriorityThreadPoolExecutor(boolean blnfifo){
        fifo = blnfifo;
    }


    /**
     * 默认工作线程数5
     */
    public PriorityThreadPoolExecutor() {
        super(THREADS_CORE_COUNT, THREADS_MAX_COUNT, THREADS_KEEP_ALIVE_TIME, THREADS_KEEP_ALIVE_UNIT, new PriorityBlockingQueue(THREADS_MAX_COUNT, fifo ? FIFO : LIFO), sThreadFactory);
    }

    private static class PriorityThreadPoolExecutorHolder{
        static final PriorityThreadPoolExecutor INSTANCE = new PriorityThreadPoolExecutor();
    }

    public static PriorityThreadPoolExecutor getInstance(){
        return PriorityThreadPoolExecutorHolder.INSTANCE;
    }


    /**
     * 判断当前线程池是否繁忙
     * @return
     */
    public boolean isBusy() {
        return getActiveCount() >= getCorePoolSize();
    }

    /**
     * 提交任务
     * @param runnable
     */
    @Override
    public void execute(Runnable runnable) {
        if (runnable instanceof PriorityRunnable) {
            ((PriorityRunnable) runnable).SEQ = SEQ_SEED.getAndIncrement();
        }
        super.execute(runnable);
    }

    /**
     * 创建线程工厂
     */
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "runnable#" + mCount.getAndIncrement());
        }
    };


    /**
     * 线程队列方式 先进先出
     */
    private static final Comparator FIFO = new Comparator() {
        @Override
        public int compare(Object lhs, Object rhs) {
            if (lhs instanceof PriorityRunnable && rhs instanceof PriorityRunnable) {
                PriorityRunnable lpr = ((PriorityRunnable) lhs);
                PriorityRunnable rpr = ((PriorityRunnable) rhs);
                int result = lpr.priority.ordinal() - rpr.priority.ordinal();
                return result == 0 ? (int) (lpr.SEQ - rpr.SEQ) : result;
            } else {
                return 0;
            }
        }
    };

    /**
     * 线程队列方式 后进先出
     */
    private static final Comparator LIFO = new Comparator() {
        @Override
        public int compare(Object lhs, Object rhs) {
            if (lhs instanceof PriorityRunnable && rhs instanceof PriorityRunnable) {
                PriorityRunnable lpr = ((PriorityRunnable) lhs);
                PriorityRunnable rpr = ((PriorityRunnable) rhs);
                int result = lpr.priority.ordinal() - rpr.priority.ordinal();
                return result == 0 ? (int) (rpr.SEQ - lpr.SEQ) : result;
            } else {
                return 0;
            }
        }
    };

}