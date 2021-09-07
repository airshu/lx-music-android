package com.shjlone.lxmusic.ui.songlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.shjlone.lxmusic.databinding.FragmentSonglistBinding
import com.shjlone.lxmusic.repository.KGRepository
import com.shjlone.lxmusic.vo.Platform
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * 歌曲列表
 */
class SongListFragment : Fragment() {

    private val TAG: String = "SongListFragment"
    private lateinit var songListViewModel: SongListViewModel
    private var _binding: FragmentSonglistBinding? = null
    private val binding get() = _binding!!
    private var adapter: SongListAdapter? = null

    private var platform: Int = -1


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        platform = arguments!!.getInt("platform", -1)
        Log.d(TAG, "platform=$platform")

        songListViewModel =
            ViewModelProvider(this).get(SongListViewModel::class.java)

        _binding = FragmentSonglistBinding.inflate(inflater, container, false)
        val root: View = binding.root

        songListViewModel.songList.observe(viewLifecycleOwner, Observer { it ->
            Log.d("#####", it.toString())
            adapter = SongListAdapter(it)
            binding.list.adapter = adapter
            var currentPage = songListViewModel.currentPage.value ?: 1
            binding.list.scrollToPosition(currentPage * songListViewModel.limit.value!!)
        })

        when (platform) {
            Platform.KUWO.key -> initKuWO()
            Platform.KUGOU.key -> initKuGou()
            Platform.QQ.key -> initQQ()
            Platform.WANGYI.key -> initWangyi()
            Platform.MIGU.key -> initMigu()
        }

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
        }

        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val b = recyclerView.canScrollVertically(1)

                if (b == false) {
                    var currentPage = songListViewModel.currentPage.value ?: 1
                    songListViewModel.getKuGouList(currentPage + 1, "8888")

                    Log.d(TAG, "-翻页-$currentPage-----> ???$b")
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
//                Log.d(TAG, "dy=$dy")
            }

        })


        return root
    }

    fun initKuWO() {
        GlobalScope.launch {
//            songListViewModel.initSongList()
//            KWRespository().getKWMusicUrl("163982269")
//            KGRepository().search("中国")
        }
    }

    //初始化酷狗的信息
    fun initKuGou() {
        if (songListViewModel.songList.value?.size == 0) {
            songListViewModel.getKuGouList(songListViewModel.currentPage.value ?: 1, "8888")
        }

        KGRepository().search("中国")
    }

    fun initQQ() {

    }

    fun initWangyi() {

    }

    fun initMigu() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

