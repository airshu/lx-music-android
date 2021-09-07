package com.shjlone.lxmusic.util

import android.content.Context
import android.view.WindowManager

/**
 * @author: a564
 * @Date 2021/5/26
 */
object DensityUtils {

    fun getWindowsWidth(context: Context): Int {
        val wm = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.width
    }

    fun getWindowsHeight(context: Context): Int {
        val wm = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return wm.defaultDisplay.height
    }
}