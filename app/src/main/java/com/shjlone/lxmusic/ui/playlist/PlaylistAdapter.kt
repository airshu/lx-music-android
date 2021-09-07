package com.shjlone.lxmusic.ui.playlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shjlone.lxmusic.R
import com.shjlone.lxmusic.data.Music
import com.shjlone.lxmusic.util.AudioPlayer
import com.shjlone.lxmusic.vo.MusicItem

/**
 * @author: a564
 * @Date 2021/5/18
 */
class PlaylistAdapter(var dataSet: List<Music>) :
    RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val songName: TextView
        val artist: TextView
        val album: TextView
        val btnPlay: View
        val btnAdd: View

        init {
            songName = view.findViewById(R.id.songName)
            artist = view.findViewById(R.id.artist)
            album = view.findViewById(R.id.album)
            btnPlay = view.findViewById(R.id.btnPlay)
            btnAdd = view.findViewById(R.id.btnAdd)
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): PlaylistAdapter.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_song_list, viewGroup, false)

        return PlaylistAdapter.ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.songName.text = dataSet[position].name
        holder.btnPlay.setOnClickListener {

        }
    }
}