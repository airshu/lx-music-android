package com.shjlone.lxmusic.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shjlone.lxmusic.vo.MusicItem

class SongItemsViewModel : ViewModel() {


    val songList = MutableLiveData<MutableList<MusicItem>>()
}