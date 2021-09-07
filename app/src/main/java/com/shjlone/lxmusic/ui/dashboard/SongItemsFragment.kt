package com.shjlone.lxmusic.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shjlone.lxmusic.databinding.FragmentDashboardBinding
import com.shjlone.lxmusic.vo.Platform

/**
 * 歌单列表
 */
class SongItemsFragment : Fragment() {

    private val TAG = "SongItemsFragment"

    private lateinit var songItemsViewModel: SongItemsViewModel
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private var adapter: SongItemsAdapter? = null
    private var platform: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        platform = arguments?.getInt("platform", -1) ?: Log.d(TAG, "platform=$platform")

        songItemsViewModel =
            ViewModelProvider(this).get(SongItemsViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        songItemsViewModel.songList.observe(viewLifecycleOwner, Observer { it ->
            Log.d("#####", it.toString())
            if (adapter == null) {
                adapter = SongItemsAdapter(it)
                binding.list.adapter = adapter
            }
            adapter?.dataSet = it
        })

        when (platform) {
            Platform.KUWO.key -> initKuWO()
            Platform.KUGOU.key -> initKuGou()
            Platform.QQ.key -> initQQ()
            Platform.WANGYI.key -> initWangYi()
            Platform.MIGU.key -> initMiGu()
        }

        return root
    }


    fun initKuWO() {}
    fun initKuGou() {}
    fun initQQ() {}
    fun initWangYi() {}
    fun initMiGu() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}