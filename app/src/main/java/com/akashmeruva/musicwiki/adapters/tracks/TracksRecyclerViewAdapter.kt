package com.akashmeruva.musicwiki.ui.GenreInfo.tracks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akashmeruva.musicwiki.R
import com.akashmeruva.musicwiki.models.Track
import com.bumptech.glide.Glide

class TracksRecyclerViewAdapter(private val listener: Tracks_Fragment): RecyclerView.Adapter<TrackViewHolder>() {

    private val items: ArrayList<Track> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tracks_item, parent, false)
        val viewHolder = TrackViewHolder(view)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {

        val currentItem = items[position]
        holder.trackName.text = currentItem.name
        Glide.with(holder.itemView.context).load(currentItem.image_link).into(holder.img)
    }

    fun update(updatedTrack: ArrayList<Track>) {
        items.clear()
        items.addAll(updatedTrack)

        notifyDataSetChanged()
    }
}

class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val trackName: TextView = itemView.findViewById(R.id.tracks_item_name)
    val img: ImageView = itemView.findViewById(R.id.tracks_item_ing)
}