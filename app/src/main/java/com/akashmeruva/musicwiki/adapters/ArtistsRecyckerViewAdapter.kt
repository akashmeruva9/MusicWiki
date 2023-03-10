package com.akashmeruva.musicwiki.ui.GenreInfo.artists

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akashmeruva.musicwiki.R
import com.akashmeruva.musicwiki.models.Artist
import com.bumptech.glide.Glide


class ArtistsRecyckerViewAdapter(private val listener: Artists_Fragment): RecyclerView.Adapter<ArtistViewHolder>() {

    private val items: ArrayList<Artist> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.artist_item, parent, false)
        val viewHolder = ArtistViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {

        val currentItem = items[position]
        holder.artistName.text = currentItem.name
        Glide.with(holder.itemView.context).load(currentItem.image_link).into(holder.img)
    }

    fun update(updatedArtist: ArrayList<Artist>) {
        items.clear()
        items.addAll(updatedArtist)

        notifyDataSetChanged()
    }
}

class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val artistName : TextView = itemView.findViewById(R.id.artist_item_name)
    val img: ImageView = itemView.findViewById(R.id.artist_item_ing)
}