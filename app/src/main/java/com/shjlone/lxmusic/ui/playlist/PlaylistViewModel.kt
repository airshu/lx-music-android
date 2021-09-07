package com.shjlone.lxmusic.ui.playlist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shjlone.lxmusic.data.Music
import com.shjlone.lxmusic.util.db.DatabaseUtil
import okhttp3.OkHttpClient

class PlaylistViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is 歌单"
    }
    val text: LiveData<String> = _text

    //总页数
    private val _totalPge = MutableLiveData<Int>().apply {
        value = 1
    }

    val totalPage: LiveData<Int> = _totalPge

    //当前页
    private val _currentPage = MutableLiveData<Int>().apply {
        value = 1
    }
    var currentPage: LiveData<Int> = _currentPage

    private val _limit = MutableLiveData<Int>().apply {
        value = 30
    }

    val limit: LiveData<Int> = _limit


    val songList = MutableLiveData<MutableList<Music>>()


    private var client: OkHttpClient

    init {
        songList.value = arrayListOf()
        client = OkHttpClient()
    }

    fun initSongList(context: Context) {
        val musicList = DatabaseUtil.getInstance(context).musicDao().selectAll()
        songList.value?.addAll(musicList)
        songList.postValue(songList.value)
    }
}