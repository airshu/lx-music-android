package com.shjlone.lxmusic.ui.rangkinglist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.shjlone.lxmusic.R
import com.shjlone.lxmusic.vo.KWItem

/**
 * @author: a564
 * @Date 2021/5/14
 */
class RankingListAdapter(var dataSet: List<KWItem>) :
    RecyclerView.Adapter<RankingListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val songName: TextView
        val artist: TextView
        val album: TextView
        val btnPlay: Button
        val btnAdd: Button

        init {
            // Define click listener for the ViewHolder's View.
            songName = view.findViewById(R.id.songName)
            artist = view.findViewById(R.id.artist)
            album = view.findViewById(R.id.album)
            btnPlay = view.findViewById(R.id.btnPlay)
            btnAdd = view.findViewById(R.id.btnAdd)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_ranking_list, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.songName.text = dataSet[position].name
        viewHolder.artist.text = dataSet[position].artist
        viewHolder.album.text = dataSet[position].album
        viewHolder.btnAdd.setOnClickListener {
            Log.d("###", "click")
        }
        viewHolder.btnAdd.setOnClickListener {
            Log.d("###", "click")
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}