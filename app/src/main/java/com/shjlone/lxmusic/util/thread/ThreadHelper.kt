package com.shjlone.lxmusic.util.thread

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * 线程工具类
 *
 * @author: a564
 * @Date 2021/5/19
 */
object ThreadHelper {

    private val poolNumber = AtomicInteger(1)
    private val corePoolsSize = Runtime.getRuntime().availableProcessors() + 1
    private val mCacheThreadPoolExecutor = initThreadPoolExecutor()
    private fun initThreadPoolExecutor(): ThreadPoolExecutor {
        val threadPoolExecutor: ThreadPoolExecutor = ThreadPoolExecutor(corePoolsSize,
            corePoolsSize,
            60L,
            TimeUnit.SECONDS,
            LinkedBlockingQueue<Runnable?>(),
            ThreadFactory { r ->
                val thread = Thread(r)
                thread.name = "threadhelper-" + poolNumber.getAndIncrement()
                thread
            })
        threadPoolExecutor.allowCoreThreadTimeOut(true)
        return threadPoolExecutor
    }

    /**
     * 主线程执行
     */
    fun runOnUiThread(run: Runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            run.run()
        } else {
            Handlers.sUIHandler.post(run)
        }
    }

    /**
     * 主线程延迟执行
     */
    fun runOnUiThread(run: Runnable, delayMillis: Long) {
        Handlers.sUIHandler.postDelayed(run, delayMillis)
    }

    /**
     * 使用自定义handler异步执行
     */
    fun runOnBackgroundThread(run: Runnable) {
        if (Looper.myLooper() == Handlers.sBackgroundHandler.getLooper()) {
            run.run()
        } else {
            Handlers.sBackgroundHandler.post(run)
        }
    }

    /**
     * 使用线程池异步执行
     */
    fun runOnAsyncThread(runnable: Runnable) {
        Log.d("ThreadHelper", "runOnAsyncThread " + Runtime.getRuntime().availableProcessors())
        mCacheThreadPoolExecutor.execute(runnable)
    }

    /**
     * 使用自定义handler执行，一次只能执行一个方法
     */
    fun runOnAsyncThread(runnable: Runnable, delayMillis: Long) {
        Handlers.sBackgroundHandler.postDelayed(Runnable {
            Log.d("ThreadHelper", "runOnAsyncThread")
            ThreadHelper.mCacheThreadPoolExecutor.execute(runnable)
        }, delayMillis)
    }

}

internal object Handlers {
    val sUIHandler = Handler(Looper.getMainLooper())
    val sBackgroundHandler: Handler = BackgroundHandlerThread.getInstance().getHandler()
}