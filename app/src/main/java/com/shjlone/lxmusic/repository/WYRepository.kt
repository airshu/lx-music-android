package com.shjlone.lxmusic.repository

import android.util.Log
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

/**
 * @author: a564
 * @Date 2021/5/18
 */
class WYRepository {

    companion object {
        val client = OkHttpClient()
    }

    fun getDetail(songmid:String) {
        val url = "https://music.163.com/weapi/v3/song/detail"



        try {
            val builder = FormBody.Builder()
//            builder.add("")
            val request = Request.Builder()
                .url(url)
                .post(builder.build())
                .addHeader("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36")
                .addHeader("Referer", "https://music.163.com/song?id=' + $songmid")
                .addHeader("origin", "https://music.163.com")
                .build()

            var call = KGRepository.client.newCall(request)
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

                    }


                    Log.d("UPDATE", "OnResponse: " + content)

                }
            })
        } catch (e: Exception) {
            Log.e("UPDATE ERROR:", "", e)
        }
    }
}