package com.shjlone.lxmusic.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.shjlone.lxmusic.R
import com.shjlone.lxmusic.databinding.FragmentGalleryBinding
import com.shjlone.lxmusic.ui.dashboard.SongItemsFragment
import com.shjlone.lxmusic.ui.rangkinglist.RankingListViewModel
import com.shjlone.lxmusic.ui.songlist.SongListFragment
import com.shjlone.lxmusic.vo.platformList

/**
 * 歌单展示
 */
class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.viewpager2.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                val fragment = SongItemsFragment()
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

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}