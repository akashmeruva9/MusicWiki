package com.akashmeruva.musicwiki.ui.GenreInfo.artists.artist_info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akashmeruva.musicwiki.models.Album
import com.akashmeruva.musicwiki.R
import com.bumptech.glide.Glide

class Artist_info_top_album_recyclerview_adapter(private val listener: Artist_info_Activity): RecyclerView.Adapter<Artist_Info_Album_Recycler_ViewHolder>() {

    private val items: ArrayList<Album> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Artist_Info_Album_Recycler_ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.album_item, parent, false)
        val viewHolder = Artist_Info_Album_Recycler_ViewHolder(view)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: Artist_Info_Album_Recycler_ViewHolder, position: Int) {

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

class Artist_Info_Album_Recycler_ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val albumName: TextView = itemView.findViewById(R.id.album_item_name)
    val artistName : TextView = itemView.findViewById(R.id.album_item_artist)
    val img: ImageView = itemView.findViewById(R.id.album_item_img)
}