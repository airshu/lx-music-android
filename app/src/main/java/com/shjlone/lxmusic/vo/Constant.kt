package com.shjlone.lxmusic.vo

/**
 * @author: a564
 * @Date 2021/5/18
 */
enum class Constant {
}

/**
 * 平台类型
 */
enum class Platform(val key: Int) {
    KUGOU(0),
    KUWO(1),
    QQ(2),
    WANGYI(3),
    MIGU(4),
}

val platformList = arrayListOf<String>().apply {
    add("酷狗音乐")
    add("酷我音乐")
    add("企鹅音乐")
    add("网易音乐")
    add("咪咕音乐")
}


