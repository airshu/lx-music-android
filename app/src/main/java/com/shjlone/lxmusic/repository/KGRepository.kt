package com.shjlone.lxmusic.repository

import android.util.Log
import com.shjlone.lxmusic.data.Music
import com.shjlone.lxmusic.util.MD5Utils
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

/**
 * @author: a564
 * @Date 2021/5/17
 */
class KGRepository {

    companion object {
        val client = OkHttpClient()
    }

    //根据关键字搜索
    fun search(keyword: String) {
        val url =
            "http://mobilecdn.kugou.com/new/app/i/search.php?keyword=$keyword&cmd=300&pagesize=10"

        try {
            val request = Request.Builder().get()
                .url(url)
                .build()

            var call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("UPDATE", "onFailure: $e")
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val content: String = response.body?.string() ?: ""
                    val json = JSONObject(content)
                    val songList = json.getJSONArray("data")
                    for (i in (0..songList.length() - 1)) {
                        val hash = (songList.get(i) as JSONObject).get("hash") as String
//                        val name = (songList.get(i) as JSONObject).get("name") as String
//                        getSongInfo(name, hash, { url ->
//                            Log.d("#@@@@###", url)
//                        })

                    }


                    Log.d("UPDATE", "OnResponse: " + content)

                }
            })
        } catch (e: Exception) {
            Log.e("UPDATE ERROR:", "", e)
        }
    }

    //获取歌曲详情
    fun getSongInfo(name: String, hash: String, callback: (Music) -> Unit) {
        val key = MD5Utils.getMD5("${hash}kgcloud")
        try {

            val httpUrl =
                HttpUrl.Builder().scheme("http").host("trackercdn.kugou.com")
                    .addPathSegment("i/")
                    .addQueryParameter("pid", "6")
                    .addQueryParameter("cmd", "3")
                    .addQueryParameter("acceptMp3", "1")
                    .addQueryParameter("hash", hash)
                    .addQueryParameter("key", key)
                    .build()
            val request = Request.Builder().get()
                .url(httpUrl)
                .build()

            var call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("UPDATE", "onFailure: $e")
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val content: String = response.body?.string() ?: ""
                    val obj = JSONObject(content)
                    val url = obj.optString("url")
                    val music = Music()
                    music.name = name
                    music.hash = hash
                    music.url = url
                    callback.invoke(music)
//                    Log.d("UPDATE####", "OnResponse: " + content)

                }
            })
        } catch (e: Exception) {
            Log.e("UPDATE ERROR:", "", e)
        }

    }


}