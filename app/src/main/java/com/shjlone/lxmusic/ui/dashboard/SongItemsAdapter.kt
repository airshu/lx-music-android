package com.shjlone.lxmusic.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.shjlone.lxmusic.R
import com.shjlone.lxmusic.vo.MusicItem

/**
 * @author: a564
 * @Date 2021/5/27
 */
class SongItemsAdapter(var dataSet: List<MusicItem>) :
    RecyclerView.Adapter<SongItemsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val songIcon: ImageView

        init {
            songIcon = view.findViewById(R.id.songIcon)
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SongItemsAdapter.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_song_item, viewGroup, false)

        return SongItemsAdapter.ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.songIcon.background = dataSet[position].songName
    }
}