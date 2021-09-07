package com.shjlone.lxmusic.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shjlone.lxmusic.vo.KWItem
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Type

/**
 * 酷我音乐接口
 * @author: a564
 * @Date 2021/5/17
 */
class KWRepository {

    companion object {
        val client = OkHttpClient()
    }

    fun getKWMusicUrl(songmid:String, type:String="128k") {
        val url = "http://ts.tempmusic.tk/url/kw/$songmid/$type"

        try {
            val request = Request.Builder().get()
                .url(url)
                .addHeader("User-Agent", "lx-music request")
                .addHeader("624868746c", "624868746c")
                .build()

            var call = client.newCall(request)
            //异步请求
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("UPDATE", "onFailure: $e")
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val gson = Gson()
                    val content: String = response.body?.string() ?: ""
                    Log.d("UPDATE", "OnResponse: " + content)
//                    val songListJson = gson.fromJson(content, listType.javaClass)
                    val json = JSONObject(content)
                    val musicListArr: JSONArray = json.get("musiclist") as JSONArray
                    for (i in (0..musicListArr.length()-1)) {
                        val jsonObj: JSONObject = musicListArr.get(i) as JSONObject
//                        Log.d("###", jsonOjb.toString())
                    }

                }
            })
        } catch (e: Exception) {
            Log.e("UPDATE ERROR:", "", e)
        }

    }


}