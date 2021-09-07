package com.shjlone.lxmusic.repository

import android.R
import android.util.Log
import okhttp3.*
import java.io.IOException
import java.io.InputStream


/**
 * @author: a564
 * @Date 2021/5/17
 */
class HttpClient {

    val TAG: String = "HttpClient"


    companion object {
        private val client = OkHttpClient()
    }

    fun getSongList() {
        val url:String = "http://musicapi.qianqian.com/v1/restserver/ting?from=android&version=7.0.2.0&channel=ppzs&operator=0&method=baidu.ting.search.hot"
        val request = Request.Builder()
            .url(url)
            .header("User-Agent", "android_7.0.2.0;baiduyinyue")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            println("Server: ${response.header("Server")}")
            println("Date: ${response.header("Date")}")
            println("Vary: ${response.headers("Vary")}")
        }
    }

    fun getKGSongList() {
        val url:String = "http://gateway.kugou.com/api/v3/search/hot_tab?signature=ee44edb9d7155821412d220bcaf509dd&appid=1005&clientver=10026&plat=0"
        val request = Request.Builder()
            .url(url)
            .header("User-Agent", "Android9-AndroidPhone-10020-130-0-searchrecommendprotocol-wifi")
            .addHeader("dfid", "1ssiv93oVqMp27cirf2CvoF1")
            .addHeader("mid", "156798703528610303473757548878786007104")
            .addHeader("clienttime", "1584257267")
            .addHeader("x-router", "msearch.kugou.com")
            .addHeader("kg-rc", "1")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            println("Server: ${response.header("Server")}")
            println("Date: ${response.header("Date")}")
            println("Vary: ${response.headers("Vary")}")
        }
    }

    fun getKWSongList() {
        val url = "http://kbangserver.kuwo.cn/ksong.s?from=pc&fmt=json&pn=0&rn=100&type=bang&data=content&id=16&show_copyright_off=0&pcmp4=1&isbang=1"
//        val request = Request.Builder().get()
//            .url(url)
//            .build()
//
//        client.newCall(request).execute().use { response ->
//            if (!response.isSuccessful) throw IOException("Unexpected code $response")
//
//            println("Server: ${response.header("Server")}")
//            println("Date: ${response.header("Date")}")
//            println("Vary: ${response.headers("Vary")}")
//        }


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
                    Log.d("UPDATE", "OnResponse: " + response.body?.string())
                }
            })
        }catch (e:Exception) {
            Log.e("UPDATE ERROR:", "", e)
        }
    }




    fun get(url: String) {
        val httpClient = OkHttpClient()

//        url = "https://www.baidu.com/"
        val getRequest: Request = Request.Builder()
            .url(url)
            .get()
            .build()

        val call: Call = httpClient.newCall(getRequest)

        Thread {
            try {
                //同步请求，要放到子线程执行
                val response: Response = call.execute()
                Log.i(TAG, "okHttpGet run: response:" + response.body?.string())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    /**
     * 异步请求
     */
    fun getAsync(url: String) {
        val httpClient = OkHttpClient()

//        url = "https://www.baidu.com/"
        val getRequest: Request = Request.Builder()
            .url(url)
            .get()
            .build()

        val call: Call = httpClient.newCall(getRequest)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                Log.i(TAG, "okHttpGet enqueue: onResponse:" + response.body!!.string())
                val body = response.body
                val string = body!!.string()
                val bytes = body!!.bytes()
                val inputStream: InputStream = body!!.byteStream()
            }


        })
    }

    fun post(url: String) {
        val httpClient = OkHttpClient()

//        MediaType contentType = MediaType.parse("text/x-markdown; charset=utf-8");
//        String content = "hello!";
//        RequestBody body = RequestBody.create(contentType, content);

        //RequestBody:fileBody,上传文件

//        MediaType contentType = MediaType.parse("text/x-markdown; charset=utf-8");
//        String content = "hello!";
//        RequestBody body = RequestBody.create(contentType, content);

        //RequestBody:fileBody,上传文件
//        val file: File = drawableToFile(this, R.mipmap.bigpic, File("00.jpg"))
//        val fileBody: RequestBody = create(MediaType.parse("image/jpg"), file)


        //RequestBody:multipartBody, 多类型 （用户名、密码、头像）


        //RequestBody:multipartBody, 多类型 （用户名、密码、头像）
        val multipartBody: MultipartBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("username", "hufeiyang")
            .addFormDataPart("phone", "123456")
//            .addFormDataPart("touxiang", "00.png", fileBody)
            .build()


        val getRequest: Request = Request.Builder()
            .url("http://yun918.cn/study/public/file_upload.php")
            .post(multipartBody)
            .build()

        val call = httpClient.newCall(getRequest)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i(
                    TAG,
                    """okHttpPost enqueue: onFailure:${call.request()} body:""" + call.request().body!!
                        .contentType()
                        .toString() + "\n IOException:" + e.message
                )
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.i(
                    TAG, """okHttpPost enqueue: onResponse:$response:""" + response.body!!.string()
                )
            }
        })
    }
}