package com.akashmeruva.musicwiki.GenreInfo.albums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akashmeruva.musicwiki.R
import com.bumptech.glide.Glide


class AlbumRecyclerViewAdapter(private val listener: Albums_Fragment): RecyclerView.Adapter<AlbumViewHolder>() {

    private val items: ArrayList<Album> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.album_item, parent, false)
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

        val currentItem = items[position]
        holder.albumName.text = currentItem.name
        holder.artistName.text = currentItem.artist
        Glide.with(holder.itemView.context).load(currentItem.image_link).into(holder.img)
    }

    fun update(updatedAlbum: ArrayList<Album>) {
        items.clear()
        items.addAll(updatedAlbum)

        notifyDataSetChanged()
    }
}

class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val albumName: TextView = itemView.findViewById(R.id.album_item_name)
    val artistName :TextView = itemView.findViewById(R.id.album_item_artist)
    val img: ImageView = itemView.findViewById(R.id.album_item_img)
}