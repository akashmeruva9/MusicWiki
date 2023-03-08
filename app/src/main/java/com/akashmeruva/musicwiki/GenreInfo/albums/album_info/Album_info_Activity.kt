package com.akashmeruva.musicwiki.GenreInfo.albums.album_info

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akashmeruva.musicwiki.Genre.MySingleton
import com.akashmeruva.musicwiki.R
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide


class Album_info_Activity : AppCompatActivity() {

    lateinit var myadapter: Album_InfoGenre_Recycler_view_Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_info)

        val albumName = intent.getStringExtra("album_name")
        val artistName = intent.getStringExtra("artist_name")
        val imagelink = intent.getStringExtra("image_link")

        val ALbumNameTv = findViewById<TextView>(R.id.album_info_item_name)
        val ArtistNameTv = findViewById<TextView>(R.id.album_artist_info_item_artist)
        val bcgImageView = findViewById<ImageView>(R.id.album_info_bcg_img)
        val description = findViewById<TextView>(R.id.album_info_tv2)
        val backBtn = findViewById<Button>(R.id.album_info_back_btn)

        ALbumNameTv.text = albumName
        ArtistNameTv.text = artistName
        Glide.with(this).load(imagelink).into(bcgImageView)


        backBtn.setOnClickListener {
            this.finish()
        }

        val manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val RecyclerView = findViewById<RecyclerView>(R.id.album_info_recycler_view)
        RecyclerView.layoutManager = manager



        val url = "https://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=2d27e848887a6c209a96fe02d7dc1f51&artist=$artistName&album=$albumName&format=json"

        val jsonObjectRequest1 = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                val jsonObject = response.getJSONObject("album")
                val tagJsonObject = jsonObject.getJSONObject("tags")
                val jsonArray = tagJsonObject.getJSONArray("tag")

                val TagArray = ArrayList<String>()
                for (i in 0 until jsonArray.length()) {
                    val JsonObject3 = jsonArray.getJSONObject(i)
                    val TagName = JsonObject3.getString("name")
                    TagArray.add(TagName)
                }

                val jsonObject2 = jsonObject.getJSONObject("wiki")
                val summary = jsonObject2.getString("summary")
                description.text = summary.substringBefore("<a")

                myadapter.update(TagArray)
            },
            {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            })

        this.let {
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest1)
        }

        myadapter = Album_InfoGenre_Recycler_view_Adapter(this)
        RecyclerView.adapter = myadapter

    }


    fun aa(){
       /* */
    }

    fun onItemClicked(s: String) {

    }
}