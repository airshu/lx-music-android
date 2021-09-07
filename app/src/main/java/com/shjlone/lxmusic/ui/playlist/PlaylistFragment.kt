package com.shjlone.lxmusic.ui.playlist

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shjlone.lxmusic.R
import com.shjlone.lxmusic.databinding.FragmentPlaylistBinding
import com.shjlone.lxmusic.ui.dialog.TipDialog
import com.shjlone.lxmusic.util.DensityUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * 播放列表
 *
 * @author: a564
 * @Date 2021/5/26
 */
class PlaylistFragment : DialogFragment() {

    private lateinit var playlistViewModel: PlaylistViewModel
    private var adapter: PlaylistAdapter? = null
    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE);
        val params = dialog?.window?.attributes
        params?.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        dialog?.window?.attributes = params

        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.playlistClearAll.setOnClickListener {
            context?.let {
                TipDialog.createConfirmDialog(it, "提示", "是否清空列表", {
                    Log.d("#####", "确认了")
                })
            }
        }

        playlistViewModel =
            ViewModelProvider(this).get(PlaylistViewModel::class.java)

        playlistViewModel.songList.observe(viewLifecycleOwner, Observer { it ->
            Log.d("#####", it.toString())
            if (adapter == null) {
                adapter = PlaylistAdapter(it)
                binding.songListview.adapter = adapter
            }
        })
        GlobalScope.launch {
            context?.let { playlistViewModel.initSongList(it) }
        }


        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.game_transparentDialog)
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.attributes?.width = context?.let { DensityUtils.getWindowsWidth(it) }
        dialog?.window?.attributes?.height = 500
        dialog?.window?.setGravity(Gravity.BOTTOM)
//        dialog?.setCanceledOnTouchOutside(false)
    }


}