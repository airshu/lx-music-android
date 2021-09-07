package com.shjlone.lxmusic.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shjlone.lxmusic.repository.KGRepository
import com.shjlone.lxmusic.util.MD5Utils
import com.shjlone.lxmusic.vo.MusicItem
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

/**
 * @author: a564
 * @Date 2021/5/18
 */
class SearchViewModel : ViewModel() {

    val songList = MutableLiveData<MutableList<MusicItem>>()

    init {
        songList.value = arrayListOf()
    }

    //根据关键字搜索
    fun search(keyword: String, type: Int) {
        if (keyword == null || keyword == "") {
            return
        }

        songList.value?.clear()
        when (type) {
            0 -> searchKuGou(keyword)
            1 -> searchQQ(keyword)
            2 -> searchWangyi(keyword)
            3 -> searchMigu(keyword)

        }
    }

    fun searchQQ(keyword: String) {

    }

    fun searchWangyi(keyword: String) {

    }

    fun searchMigu(keyword: String) {

    }


    fun searchKuGou(keyword: String) {
        val url =
            "http://mobilecdn.kugou.com/new/app/i/search.php?keyword=$keyword&cmd=300&pagesize=10"

        try {
            val request = Request.Builder().get()
                .url(url)
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
                    val songListArr = json.getJSONArray("data")
                    for (i in (0..songListArr.length() - 1)) {
                        val jsonObj = songListArr.get(i) as JSONObject
                        val hash = jsonObj.get("hash") as String
                        val item = MusicItem()
                        item.songName = jsonObj.optString("filename")
                        item.id = jsonObj.optString("id")
                        item.hash = hash
                        songList.value?.add(item)
                    }
                    songList.postValue(songList.value)
                }
            })
        } catch (e: Exception) {
            Log.e("UPDATE ERROR:", "", e)
        }
    }

    //获取歌曲详情
    fun getSongInfo(hash: String, callback: (String) -> Unit) {
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

            var call = KGRepository.client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("UPDATE", "onFailure: $e")
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val content: String = response.body?.string() ?: ""
                    val obj = JSONObject(content)
                    val url = obj.optString("url")
                    callback.invoke(url)
//                    Log.d("UPDATE####", "OnResponse: " + content)

                }
            })
        } catch (e: Exception) {
            Log.e("UPDATE ERROR:", "", e)
        }

    }
}