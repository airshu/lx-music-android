package com.shjlone.lxmusic.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author: a564
 * @Date 2021/5/19
 */
@Entity
data class Music(
    @PrimaryKey var uid: Int?=null,
    @ColumnInfo(name = "name") var name: String?="", //歌曲名字
    @ColumnInfo(name = "url") var url: String?="",  //歌曲的播放地址
    @ColumnInfo(name = "hash") var hash: String?="" //歌曲的hash值
)