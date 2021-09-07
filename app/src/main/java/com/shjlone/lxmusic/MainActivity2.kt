package com.shjlone.lxmusic

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ImageView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.exoplayer2.*
import com.shjlone.lxmusic.databinding.ActivityMain2Binding
import com.shjlone.lxmusic.event.MusicEvent
import com.shjlone.lxmusic.repository.KGRepository
import com.shjlone.lxmusic.ui.base.BaseActivity
import com.shjlone.lxmusic.ui.playlist.PlaylistFragment
import com.shjlone.lxmusic.ui.search.SearchActivity
import com.shjlone.lxmusic.util.AudioPlayer
import com.shjlone.lxmusic.util.OnAudioProgressUpdateListener
import com.shjlone.lxmusic.util.SingletonUtil
import com.shjlone.lxmusic.util.db.DatabaseUtil
import com.shjlone.lxmusic.util.player.PlayerManager
import com.shjlone.lxmusic.util.thread.ThreadHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class MainActivity2 : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding

//    private lateinit var player: SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        EventBus.getDefault().register(this)
    }

    override fun initView() {
        super.initView()

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }

    override fun initListener() {
        super.initListener()
//        binding.appBarMain.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        binding.appBarMain.toolbar.setOnMenuItemClickListener { item ->
            Log.d("###", "1111打开搜索页")
            startActivity(Intent(this, SearchActivity::class.java))
            true
        }

        binding.navView.setNavigationItemSelectedListener { item ->
            binding.drawerLayout.closeDrawers()
            when (item.itemId) {
                R.id.nav_home -> Log.d("###", "home")
                R.id.nav_gallery -> Log.d("###", "nav_gallery")
                R.id.nav_slideshow -> Log.d("###", "nav_slideshow")
                else -> Log.d("####", "###")
            }
            true
        }


        binding.appBarMain.btnSonglist.setOnClickListener { it ->
            changeToGalleryList()
        }
        binding.appBarMain.btnRankingList.setOnClickListener { it ->
            changeToRankingList()
        }
        binding.appBarMain.btnHome.setOnClickListener { it ->
            changeToHome()
        }

//        player = SimpleExoPlayer.Builder(applicationContext).build()
//        player.addListener(object : Player.Listener {
//            override fun onPlaybackStateChanged(state: Int) {
//                super.onPlaybackStateChanged(state)
//                when (state) {
//                    ExoPlayer.STATE_IDLE -> Log.d(TAG, "state=$state STATE_IDLE")
//                    ExoPlayer.STATE_READY -> Log.d(TAG, "state=$state STATE_READY")
//                    ExoPlayer.STATE_BUFFERING -> Log.d(TAG, "state=$state STATE_BUFFERING")
//                    ExoPlayer.STATE_ENDED -> Log.d(TAG, "state=$state STATE_ENDED")
//                }
//            }
//
//        })
        binding.appBarMain.playerView.player = PlayerManager.getInstance().getPlayer()

//        player.addMediaItem(MediaItem.fromUri("http://fs.w.kugou.com/202105251750/bc85cea694225f61ac3d13b589b7789d/KGTX/CLTX001/2322b3637110a46c0f250c2c91d11f9e.mp3"))


        GlobalScope.launch {
            try {
//                val music: Music = Music(null, "haha", "123123", "http://baidu.com/sxsdfdf.mp3")
//                DatabaseUtil.getInstance(applicationContext).musicDao().insert(music)
//                DatabaseUtil.getInstance(applicationContext).musicDao().deleteAll()
//                val musicList = DatabaseUtil.getInstance(applicationContext).musicDao().selectAll()

//                for (music in musicList) {
//                    Log.d(TAG, "music:" + music.toString())
//                }
                SingletonUtil.instance.yyyy()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

//        for (i in 0..10) {
//            ThreadHelper.runOnAsyncThread({
//                SingletonUtil.instance.yyyy()
//            })
//        }

        val btnPlaylist = findViewById<ImageView>(R.id.btn_playlist)
        btnPlaylist.setOnClickListener {
            val playlistFragment = PlaylistFragment()
            playlistFragment.show(supportFragmentManager, "playlistframent");

        }
//        val btnPlayPause = findViewById<ImageView>(R.id.exo_play_pause)
//        btnPlayPause.setOnClickListener {
//            if (PlayerManager.getInstance().getPlayer()?.isPlaying ?: false) {
//                PlayerManager.getInstance().getPlayer()?.stop()
//            } else {
//                PlayerManager.getInstance().getPlayer()?.prepare()
//                PlayerManager.getInstance().getPlayer()?.play()
//            }
//        }


        AudioPlayer.setOnAudioProgressUpdateListener(object : OnAudioProgressUpdateListener {
            override fun update(duration: Int, currentPosition: Int) {
                Log.d("update", "$duration ---- $currentPosition")
            }
        })

    }

    fun testThread() {
        ThreadHelper.runOnUiThread(Runnable {
            Log.d("###", "runOnUiThread˛ " + Thread.currentThread().name)
        })
        ThreadHelper.runOnBackgroundThread(Runnable {
            Log.d("###", "runOnBackgroundThread " + Thread.currentThread().name)
        })

        ThreadHelper.runOnAsyncThread(Runnable {
            Log.d("###", "runOnAsyncThread " + Thread.currentThread().name)
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_activity2, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun changeToHome() {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        navController.navigate(R.id.nav_home)
    }

    fun changeToGalleryList() {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        navController.navigate(R.id.nav_gallery_list)
    }

    fun changeToRankingList() {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        navController.navigate(R.id.nav_ranking_list)
    }

    @Subscribe
    fun onMessageEvent(event: MusicEvent) {
        event.hash?.let {
            KGRepository().getSongInfo(event.name ?: "", it, callback = { music ->
//                AudioPlayer().load(this, Uri.parse(it))
                Log.d(TAG, "===========getSongInfo===============$music")
                if (music.url?.length == 0) {
                    return@getSongInfo
                }
//                val music: Music = Music(null, "haha", "123123", it)
                DatabaseUtil.getInstance(applicationContext).musicDao().insert(music)
                ThreadHelper.runOnUiThread {
                    if (music.url?.length == 0) {
                        Toast.makeText(this, "无法播放 $music", Toast.LENGTH_SHORT)
                        return@runOnUiThread
                    }
                    Log.d(
                        TAG,
                        " 播放 $music  列表个数：${
                            PlayerManager.getInstance().getPlayer()?.mediaItemCount
                        }"
                    )

                    PlayerManager.getInstance().getPlayer()?.stop()
                    PlayerManager.getInstance().getPlayer()
                        ?.addMediaItem(MediaItem.fromUri(music?.url ?: ""))
                    PlayerManager.getInstance().getPlayer()
                        ?.seekTo(
                            PlayerManager.getInstance().getPlayer()?.mediaItemCount ?: 0,
                            C.TIME_UNSET
                        )
//                    player.playWhenReady = true
                    PlayerManager.getInstance().getPlayer()?.prepare()
                    PlayerManager.getInstance().getPlayer()?.play()
                }
//                AudioPlayer.load(this, it)

            })
        }


    }
}