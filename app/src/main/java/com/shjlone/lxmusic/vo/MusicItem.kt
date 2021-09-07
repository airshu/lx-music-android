package com.shjlone.lxmusic.vo

/**
 * @author: a564
 * @Date 2021/5/18
 */
data class MusicItem(
    var id: String? = null,
    var songName: String? = null,
    var artist: String? = null, //歌手名字
    var album: String? = null, //专辑名字
    var albumid: String? = null, //专辑id
    var hash:String? = null,
    var artistid: String? = null
)
