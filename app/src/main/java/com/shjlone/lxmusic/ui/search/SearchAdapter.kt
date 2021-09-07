package com.shjlone.lxmusic.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shjlone.lxmusic.R
import com.shjlone.lxmusic.event.MusicEvent
import com.shjlone.lxmusic.vo.MusicItem
import org.greenrobot.eventbus.EventBus

/**
 * @author: a564
 * @Date 2021/5/18
 */
class SearchAdapter(var dataSet: List<MusicItem>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val songName: TextView
        val btnPlay: View
        val btnAdd: View

        init {
            songName = view.findViewById(R.id.songName)
            btnPlay = view.findViewById(R.id.btnPlay)
            btnAdd = view.findViewById(R.id.btnAdd)
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SearchAdapter.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_search, viewGroup, false)

        return SearchAdapter.ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.songName.text = dataSet[position].songName
        holder.btnPlay.setOnClickListener {
            val event = MusicEvent()
            event.hash = dataSet[position].hash
            event.name = dataSet[position].songName
            EventBus.getDefault().post(event)
        }
    }

}