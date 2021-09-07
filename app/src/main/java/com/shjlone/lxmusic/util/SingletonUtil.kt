package com.shjlone.lxmusic.util

import android.util.Log
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.kotlin.subscribeBy

/**
 * @author: a564
 * @Date 2021/5/26
 */
class SingletonUtil {
    companion object {
        val instance: SingletonUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SingletonUtil()
        }
    }

    fun yyyy() {

        val arr1 = mutableListOf<String>()

    }

    fun xxx() {
        val arr = arrayListOf<String>()
    }
}


