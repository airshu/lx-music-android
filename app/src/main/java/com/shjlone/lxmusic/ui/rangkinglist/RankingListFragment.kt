package com.shjlone.lxmusic.ui.rangkinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.shjlone.lxmusic.databinding.FragmentRankingListBinding
import com.shjlone.lxmusic.ui.base.BaseFragment
import com.shjlone.lxmusic.ui.songlist.SongListFragment
import com.shjlone.lxmusic.vo.platformList

/**
 * 排行榜
 */
class RankingListFragment : BaseFragment() {

    private lateinit var rankingListViewModel: RankingListViewModel
    private var _binding: FragmentRankingListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRankingListBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        var array = arrayListOf<String>()
//        for (index in 1..100) {
//            array.add("hello $index")
//        }
//        adapter = RankingListAdapter(array)
//        binding.list.adapter = adapter

//        val textView: TextView = binding.textNotifications
//        rankingListViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        binding.viewpager2.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                val fragment = SongListFragment()
                val bundle = Bundle()
                bundle.putInt("platform", position)
                fragment.arguments = bundle
                return fragment
            }

            override fun getItemCount(): Int {
                return platformList.size
            }
        }

        TabLayoutMediator(binding.tabLayout, binding.viewpager2) { tab, position ->
            tab.text = platformList.get(position)

        }.attach()



        rankingListViewModel =
            ViewModelProvider(this).get(RankingListViewModel::class.java)


        return root
    }


    override fun initData() {
        super.initData()

    }

    override fun initView() {
        super.initView()

    }

    override fun initListener() {
        super.initListener()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}