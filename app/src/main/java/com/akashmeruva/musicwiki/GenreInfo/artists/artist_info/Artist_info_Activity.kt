package com.akashmeruva.musicwiki.GenreInfo.artists.artist_info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akashmeruva.musicwiki.Genre.MySingleton
import com.akashmeruva.musicwiki.GenreInfo.albums.Album
import com.akashmeruva.musicwiki.GenreInfo.albums.AlbumRecyclerViewAdapter
import com.akashmeruva.musicwiki.GenreInfo.albums.album_info.Album_InfoGenre_Recycler_view_Adapter
import com.akashmeruva.musicwiki.GenreInfo.tracks.Track
import com.akashmeruva.musicwiki.GenreInfo.tracks.TracksRecyclerViewAdapter
import com.akashmeruva.musicwiki.R
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide

class Artist_info_Activity : AppCompatActivity() {

    lateinit var genreadapter: Artist_InfoGenre_Recycler_view_Adapter
    lateinit var albumadapter: Artist_info_top_album_recyclerview_adapter
    lateinit var tracksadapter: Artist_info_top_tracks_recyclerview_adapter

    var artistName : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist_info)

         artistName = intent.getStringExtra("artist_name")
        val imagelink = intent.getStringExtra("image_link")

        val artistNameTv = findViewById<TextView>(R.id.artist_info_item_name)
        val countNumber = findViewById<TextView>(R.id.artist_player_count)
        val followers = findViewById<TextView>(R.id.artist_followers)
        val bcgImageView = findViewById<ImageView>(R.id.artist_info_bcg_img)
        val description = findViewById<TextView>(R.id.artist_info_tv2)
        val backBtn = findViewById<Button>(R.id.artist_info_back_btn)

        artistNameTv.text = artistName
        Glide.with(this).load(imagelink).into(bcgImageView)


        backBtn.setOnClickListener {
            this.finish()
        }

        val manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val RecyclerView = findViewById<RecyclerView>(R.id.artist_recycler_view)
        RecyclerView.layoutManager = manager


        val url = "https://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=$artistName&api_key=2d27e848887a6c209a96fe02d7dc1f51&format=json"

        val jsonObjectRequest1 = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                val jsonObject = response.getJSONObject("artist")
                val tagJsonObject = jsonObject.getJSONObject("tags")
                val jsonArray = tagJsonObject.getJSONArray("tag")

                val TagArray = ArrayList<String>()
                for (i in 0 until jsonArray.length()) {
                    val JsonObject3 = jsonArray.getJSONObject(i)
                    val TagName = JsonObject3.getString("name")
                    TagArray.add(TagName)
                }
                genreadapter.update(TagArray)
                val bioObject = jsonObject.getJSONObject("bio")
                val summary = bioObject.getString("summary")
                description.text = summary.substringBefore("<a")

                val jsonobject3 = jsonObject.getJSONObject("stats")
                val playcount = jsonobject3.getString("playcount")
                val lizteners = jsonobject3.getString("listeners")

                countNumber.text = "$playcount \n play count"
                followers.text = "$lizteners \n followers"
            },
            {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            })

        this.let {
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest1)
        }

        genreadapter = Artist_InfoGenre_Recycler_view_Adapter(this)
        RecyclerView.adapter = genreadapter

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



        val Album_manager = GridLayoutManager(this , 3)
        loadalbums()
        val AlbumsRecyclerView = this.findViewById<RecyclerView>(R.id.artist_Albums_recycler_view)
        AlbumsRecyclerView.layoutManager = Album_manager
        albumadapter = Artist_info_top_album_recyclerview_adapter(this)
        AlbumsRecyclerView.adapter = albumadapter

        AlbumsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!recyclerView.canScrollVertically(1)) {
                    loadalbums()
                }
            }
        })


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        val TrackManager = GridLayoutManager(this , 3)
        loadtracks()
        val TracksRecyclerView = this.findViewById<RecyclerView>(R.id.artist_Tracks_recycler_view)
        TracksRecyclerView.layoutManager = TrackManager
        tracksadapter = Artist_info_top_tracks_recyclerview_adapter(this)
        TracksRecyclerView.adapter = tracksadapter

        TracksRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!recyclerView.canScrollVertically(1)) {
                    loadtracks()
                }
            }
        })

    }




    fun loadalbums() {

        val url = "https://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist=$artistName&api_key=2d27e848887a6c209a96fe02d7dc1f51&format=json&format=json"

        val jsonObjectRequest1 = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                val jsonObject = response.getJSONObject("topalbums")
                val jsonArray = jsonObject.getJSONArray("album")

                val AlbumsArray = ArrayList<Album>()
                for (i in 0 until jsonArray.length()) {
                    val JsonObject1 = jsonArray.getJSONObject(i)
                    val AlbumName = JsonObject1.getString("name")
                    val jsonobject2 = JsonObject1.getJSONObject("artist")
                    val ArtistName = jsonobject2.getString("name")
                    val jsonArray3 = JsonObject1.getJSONArray("image")
                    val jsonobject4 = jsonArray3.getJSONObject(3)
                    val image_link = jsonobject4.getString("#text")

                    AlbumsArray.add(Album(AlbumName , ArtistName , image_link))
                }
                albumadapter.update(AlbumsArray)
            },
            {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            })

        this.let {
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest1)
        }
    }

    fun loadtracks() {

        val url = "https://ws.audioscrobbler.com/2.0/?method=artist.gettoptracks&artist=$artistName&api_key=2d27e848887a6c209a96fe02d7dc1f51&format=json"

        val jsonObjectRequest1 = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                val jsonObject = response.getJSONObject("toptracks")
                val jsonArray = jsonObject.getJSONArray("track")

                val tracksArray = ArrayList<Track>()

                for (i in 0 until jsonArray.length()) {
                    val JsonObject1 = jsonArray.getJSONObject(i)
                    val ArtistName = JsonObject1.getString("name")
                    val jsonArray3 = JsonObject1.getJSONArray("image")
                    val jsonobject4 = jsonArray3.getJSONObject(3)
                    val image_link = jsonobject4.getString("#text")

                    tracksArray.add(Track(ArtistName , image_link))
                }
                tracksadapter.update(tracksArray)

            },
            {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            })

        this.let {
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest1)
        }
    }

}