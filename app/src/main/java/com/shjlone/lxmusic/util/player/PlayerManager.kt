package com.shjlone.lxmusic.util.player

import android.content.Context
import android.util.Log
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.shjlone.lxmusic.data.Music
import com.shjlone.lxmusic.util.db.DatabaseUtil
import com.shjlone.lxmusic.util.thread.ThreadHelper

/**
 * 音乐管理器
 *
 * @author: a564
 * @Date 2021/5/26
 */
class PlayerManager private constructor() {
    private val TAG = "PlayerManager"

    /**
     * 当前音乐列表
     */
    private var musicList: List<Music>? = null
    private var player: SimpleExoPlayer? = null

    companion object {
        @Volatile
        private var instance: PlayerManager? = null

        fun getInstance(): PlayerManager {
            val i = instance
            if (i != null) {
                return i
            }

            return synchronized(this) {
                val i2 = instance
                if (i2 != null) {
                    i2
                } else {
                    val created = PlayerManager()
                    instance = created
                    created
                }
            }
        }
    }


    fun init(context: Context) {
        player = SimpleExoPlayer.Builder(context).build()
        player?.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                super.onPlaybackStateChanged(state)
                when (state) {
                    ExoPlayer.STATE_IDLE -> Log.d(TAG, "state=$state STATE_IDLE")
                    ExoPlayer.STATE_READY -> Log.d(TAG, "state=$state STATE_READY")
                    ExoPlayer.STATE_BUFFERING -> Log.d(TAG, "state=$state STATE_BUFFERING")
                    ExoPlayer.STATE_ENDED -> Log.d(TAG, "state=$state STATE_ENDED")
                }
            }

        })

        musicList = DatabaseUtil.getInstance(context).musicDao().selectAll()
        ThreadHelper.runOnUiThread({
            musicList?.let {
                for (music in it) {
                    Log.d(TAG, "music:" + music.toString())
                    player?.addMediaItem(MediaItem.fromUri(music?.url ?: ""))
                }
            }
        })


    }

    fun getPlayer(): SimpleExoPlayer? {
        return player
    }

    fun getMusicList(): List<Music>? {
        return musicList
    }


}