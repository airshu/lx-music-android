package com.shjlone.lxmusic

import android.app.Application
import com.shjlone.lxmusic.util.player.PlayerManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 *
 * @Date 2021/5/13
 *
 */
class LXMusicApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        GlobalScope.launch {
            init()
        }

    }

    fun init() {
        PlayerManager.getInstance().init(applicationContext)
    }
}