package com.akashmeruva.musicwiki.GenreInfo.artists.artist_info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akashmeruva.musicwiki.GenreInfo.tracks.Track
import com.akashmeruva.musicwiki.GenreInfo.tracks.Tracks_Fragment
import com.akashmeruva.musicwiki.R
import com.bumptech.glide.Glide

class Artist_info_top_tracks_recyclerview_adapter (private val listener: Artist_info_Activity): RecyclerView.Adapter<Artist_info_toptracksViewHolder>() {

    private val items: ArrayList<Track> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Artist_info_toptracksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tracks_item, parent, false)
        val viewHolder = Artist_info_toptracksViewHolder(view)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: Artist_info_toptracksViewHolder, position: Int) {

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

class Artist_info_toptracksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val trackName: TextView = itemView.findViewById(R.id.tracks_item_name)
    val img: ImageView = itemView.findViewById(R.id.tracks_item_ing)
}