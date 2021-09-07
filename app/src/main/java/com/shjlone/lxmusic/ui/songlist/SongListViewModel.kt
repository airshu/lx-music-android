package com.shjlone.lxmusic.ui.songlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shjlone.lxmusic.vo.KWItem
import com.shjlone.lxmusic.vo.MusicItem
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Type

class SongListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is 歌单"
    }
    val text: LiveData<String> = _text

    //总页数
    private val _totalPge = MutableLiveData<Int>().apply {
        value = 1
    }

    val totalPage: LiveData<Int> = _totalPge

    //当前页
    private val _currentPage = MutableLiveData<Int>().apply {
        value = 1
    }
    var currentPage: LiveData<Int> = _currentPage

    private val _limit = MutableLiveData<Int>().apply {
        value = 30
    }

    val limit:LiveData<Int> = _limit


    val songList = MutableLiveData<MutableList<MusicItem>>()


    private var client: OkHttpClient

    init {
        songList.value = arrayListOf()
        client = OkHttpClient()
    }

    fun getKWSongList() {

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
                    for (i in (0..musicListArr.length() - 1)) {
                        val jsonObj: JSONObject = musicListArr.get(i) as JSONObject
                        val item = KWItem()
                        item.id = jsonObj.get("id") as String
                        item.name = jsonObj.get("name") as String
                        item.artist = jsonObj.get("artist") as String
                        item.artistid = jsonObj.get("artistid") as String
                        item.album = jsonObj.get("album") as String
                        item.albumid = jsonObj.get("albumid") as String
                        item.score100 = jsonObj.get("score100") as String
//                        songList.value?.add(item)
//                        Log.d("###", jsonOjb.toString())
                    }
                    songList.postValue(songList.value)

                }
            })
        } catch (e: Exception) {
            Log.e("UPDATE ERROR:", "", e)
        }
    }

    /**
     * 获取酷狗音乐的列表信息
     * @param pageid 页面id
     * @param id  类型
     */
    fun getKuGouList(pageid: Int, id: String) {
        if (pageid > _totalPge.value!!) {
            return
        }

        val url = "http://www2.kugou.kugou.com/yueku/v9/rank/home/${pageid}-${id}.html"
        try {
            val request = Request.Builder().get()
                .url(url)
                .build()

            var call = client.newCall(request)
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("getKuGouList", "onFailure: $e")
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val html: String = response.body?.string() ?: ""
//                    Log.d("getKuGouList", "OnResponse: " + html)
                    val total = """total: '\d+',""".toRegex().find(html)?.value?.split("'")?.get(1)
                        ?: ""
                    _totalPge.postValue(total.toInt())
                    val page = """page: '\d+',""".toRegex().find(html)?.value?.split("'")?.get(1)
                        ?: ""
                    _currentPage.postValue(page.toInt())

                    //每一页的数量
                    val limit =
                        """pagesize: '\d+',""".toRegex().find(html)?.value?.split("'")?.get(1)
                            ?: 0

                    val listData = """global\.features = (\[.+\]);""".toRegex().find(html)
                    val listDataArr = JSONArray(listData!!.groupValues[1])
                    for (i in 0..listDataArr.length() - 1) {
                        val jsonObj: JSONObject = listDataArr.get(i) as JSONObject
                        val item = MusicItem()
                        item.songName = jsonObj.optString("songname")
                        item.artist = jsonObj.optString("singername")
                        item.id = jsonObj.optString("id")
                        songList.value?.add(item)
                    }
                    songList.postValue(songList.value)

                }
            })
        } catch (e: Exception) {
            Log.e("getKuGouList ERROR:", "", e)
        }

    }
}