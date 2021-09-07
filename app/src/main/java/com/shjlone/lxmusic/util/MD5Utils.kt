package com.shjlone.lxmusic.util

import java.math.BigInteger
import java.security.MessageDigest

/**
 * MD5工具类
 *
 * @author: a564
 * @Date 2021/5/17
 */
object MD5Utils {
    private val TAG = "MD5"


    fun getMD5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}