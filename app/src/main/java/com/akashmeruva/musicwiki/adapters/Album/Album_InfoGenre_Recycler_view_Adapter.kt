package com.akashmeruva.musicwiki.ui.GenreInfo.albums.album_info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akashmeruva.musicwiki.R

class Album_InfoGenre_Recycler_view_Adapter(private val listener: AlbumInfoActivity): RecyclerView.Adapter<AlbumViewHolder>() {

    private val items: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.genre_item, parent, false)
        val viewHolder = AlbumViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {

        holder.titleView.text = items[position]
    }

    fun update(updatedNews: ArrayList<String>) {
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }
}

class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.genre_text)
}