package com.shjlone.lxmusic.ui.rangkinglist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shjlone.lxmusic.vo.KWItem
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Type


class RankingListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is 排行榜"
    }
    val text: LiveData<String> = _text

    val songList = MutableLiveData<MutableList<KWItem>>()

    init {
        songList.value = arrayListOf()
    }

    fun initSongList() {
//        HttpClient().getSongList()
//        HttpClient().getKGSongList()
        getKWSongList()
    }

    fun getKWSongList() {
        val client = OkHttpClient()
        val url =
            "http://kbangserver.kuwo.cn/ksong.s?from=pc&fmt=json&pn=0&rn=100&type=bang&data=content&id=16&show_copyright_off=0&pcmp4=1&isbang=1"
        try {
            val request = Request.Builder().get()
                .url(url)
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
                    val listType: Type = object : TypeToken<ArrayList<KWItem?>?>() {}.type
                    val content: String = response.body?.string() ?: ""
                    Log.d("UPDATE", "OnResponse: " + content)
//                    val songListJson = gson.fromJson(content, listType.javaClass)
                    val json = JSONObject(content)
                    val musicListArr: JSONArray = json.get("musiclist") as JSONArray
                    for (i in (0..musicListArr.length()-1)) {
                        val jsonObj:JSONObject = musicListArr.get(i) as JSONObject
                        val item = KWItem()
                        item.id = jsonObj.get("id") as String
                        item.name = jsonObj.get("name") as String
                        item.artist = jsonObj.get("artist") as String
                        item.artistid = jsonObj.get("artistid") as String
                        item.album = jsonObj.get("album") as String
                        item.albumid = jsonObj.get("albumid") as String
                        item.score100 = jsonObj.get("score100") as String
                        songList.value?.add(item)
//                        Log.d("###", jsonOjb.toString())
                    }
                    songList.postValue(songList.value)

                }
            })
        } catch (e: Exception) {
            Log.e("UPDATE ERROR:", "", e)
        }
    }


}