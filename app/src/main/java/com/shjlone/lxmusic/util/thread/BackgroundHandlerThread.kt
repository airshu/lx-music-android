package com.shjlone.lxmusic.util.thread

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message

/**
 * @author: a564
 * @Date 2021/5/19
 */
class BackgroundHandlerThread {

    companion object {
        fun getInstance(): BackgroundHandlerThread {
            return Holder._instance
        }
    }

    private object Holder {
        val _instance = BackgroundHandlerThread()

        init {
            _instance.init()
        }
    }


    private var mHandlerThread: HandlerThread? = null
    private lateinit var mHandler: Handler

    private fun init() {
        mHandlerThread = HandlerThread(BackgroundHandlerThread::class.java.simpleName)
        mHandlerThread!!.start()
        mHandler = object : Handler(mHandlerThread!!.looper) {
            override fun handleMessage(msg: Message) {}
        }
    }

    fun getLooper(): Looper? {
        return mHandlerThread!!.looper
    }

    fun getHandler(): Handler {
        return mHandler
    }
}