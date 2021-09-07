package com.shjlone.lxmusic.util

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaPlayer.OnBufferingUpdateListener
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log


/**
 * 音频播放器，播放网络歌曲
 *
 * @author: a564
 * @Date 2021/5/18
 */
object AudioPlayer : MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
    private val PROGRESS_UPDATE_PERIOD = 100
    private val MSG_UPDATE_PROGRESS = 1
    private val TAG: String = "AudioPlayer"
    private lateinit var mediaPlayer: MediaPlayer
    val ACTION_AUDIO_CONTROL = "CONTROL"
    val ACTION_PLAY = "PLAY"
    val ACTION_PAUSE = "PAUSE"
    val ACTION_RESUME = "RESUME" //初始状态

    private var duration = 0
    private var isComplete = false
    var status: String = ACTION_RESUME

    private var onAudioProgressUpdateListener: OnAudioProgressUpdateListener? = null
    private var onErrorListener: MediaPlayer.OnErrorListener? = null
    private var onBufferingUpdateListener: OnBufferingUpdateListener? = null


    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            Log.d(TAG, "UpdateListener$duration  ---> ${mediaPlayer.currentPosition}")
            onAudioProgressUpdateListener?.update(duration, mediaPlayer.currentPosition)

            if (!isComplete) {
                this.sendEmptyMessageDelayed(MSG_UPDATE_PROGRESS, PROGRESS_UPDATE_PERIOD.toLong())
            }
        }
    }

    init {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
            override fun onPrepared(mp: MediaPlayer?) {
                duration = mediaPlayer.getDuration()
                Log.d(TAG, "onPrepared  $duration")
                play()
                status = ACTION_PLAY
            }
        })
        mediaPlayer.setOnErrorListener(this)
        mediaPlayer.setOnCompletionListener(this)
    }

    fun load(context: Context, url: String) {
        Log.d(TAG, "load url=$url")
        try {
            stop()
            reset()
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepareAsync()
            handler.sendEmptyMessage(MSG_UPDATE_PROGRESS)
        } catch (e: Exception) {
            Log.e(TAG, "${e.toString()}")
        }
    }

    fun play() {
        if (!mediaPlayer.isPlaying()) {
            Log.d(TAG, "开始play")
            mediaPlayer.start()
        }
    }

    fun pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause()
            status = ACTION_RESUME
        }
    }

    fun reset() {
        Log.d(TAG, "reset")
        mediaPlayer.reset()
    }

    fun release() {
        mediaPlayer.release()
    }

    fun stop() {
        if (mediaPlayer.isPlaying) {
            Log.d(TAG, "stop")
            mediaPlayer.stop()
            status = ACTION_RESUME
        }
    }

    fun setOnErrorListener(onErrorListener: MediaPlayer.OnErrorListener) {
        this.onErrorListener = onErrorListener
    }

    fun setOnBufferingUpdateListener(onBufferingUpdateListener: OnBufferingUpdateListener) {
        this.onBufferingUpdateListener = onBufferingUpdateListener
    }

    fun setOnAudioProgressUpdateListener(onAudioProgressUpdateListener: OnAudioProgressUpdateListener?) {
        this.onAudioProgressUpdateListener = onAudioProgressUpdateListener
    }

    override fun onCompletion(mp: MediaPlayer?) {
        Log.d(TAG, "=--setOnCompletionListener---> $mp")
        isComplete = true
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        Log.e(TAG, "onError ----- $what -----  $extra")
        return true
    }


}

interface OnAudioProgressUpdateListener {
    fun update(duration: Int, currentPosition: Int)
}