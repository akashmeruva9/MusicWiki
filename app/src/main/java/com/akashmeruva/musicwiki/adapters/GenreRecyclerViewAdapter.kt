package com.akashmeruva.musicwiki.ui.Genre

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akashmeruva.musicwiki.R

class GenreRecyclerViewAdapter(private val listener: Genre_Activity): RecyclerView.Adapter<GenreViewHolder>() {

    private val items: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.genre_item, parent, false)
        val viewHolder = GenreViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {

        holder.titleView.text = items[position]
    }

    fun update(updatedNews: ArrayList<String>) {
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }
}

class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.genre_text)
}