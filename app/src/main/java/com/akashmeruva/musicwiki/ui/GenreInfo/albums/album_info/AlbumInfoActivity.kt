package com.akashmeruva.musicwiki.ui.GenreInfo.albums.album_info

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.akashmeruva.musicwiki.adapters.Genre.MySingleton
import com.akashmeruva.musicwiki.databinding.ActivityAlbumInfoBinding
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide


class AlbumInfoActivity : AppCompatActivity() {

    lateinit var myadapter: Album_InfoGenre_Recycler_view_Adapter
    private lateinit var binding : ActivityAlbumInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAlbumInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val albumName = intent.getStringExtra("album_name")
        val artistName = intent.getStringExtra("artist_name")
        val imagelink = intent.getStringExtra("image_link")

        binding.albumInfoItemName.text = albumName
        binding.albumArtistInfoItemArtist.text = artistName
        Glide.with(this).load(imagelink).into(binding.albumInfoBcgImg)

        binding.albumInfoBackBtn.setOnClickListener {
            this.finish()
        }

        val manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.albumInfoRecyclerView.layoutManager = manager

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
                binding.albumInfoTv2.text = summary.substringBefore("<a")

                myadapter.update(TagArray)
            },
            {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            })

        this.let {
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest1)
        }

        myadapter = Album_InfoGenre_Recycler_view_Adapter(this)
        binding.albumInfoRecyclerView.adapter = myadapter

    }

    fun onItemClicked(s: String) {

    }
}