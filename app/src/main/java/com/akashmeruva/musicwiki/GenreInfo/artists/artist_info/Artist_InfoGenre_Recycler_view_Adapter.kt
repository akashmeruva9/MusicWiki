package com.akashmeruva.musicwiki.GenreInfo.artists.artist_info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akashmeruva.musicwiki.R

class Artist_InfoGenre_Recycler_view_Adapter(private val listener: Artist_info_Activity): RecyclerView.Adapter<ArtistInfoViewHolder>() {

    private val items: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.genre_item, parent, false)
        val viewHolder = ArtistInfoViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ArtistInfoViewHolder, position: Int) {

        holder.titleView.text = items[position]
    }

    fun update(updatedNews: ArrayList<String>) {
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }
}

class ArtistInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.genre_text)
}