package com.akashmeruva.musicwiki.ui.GenreInfo.artists.artist_info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akashmeruva.musicwiki.adapters.Genre.MySingleton
import com.akashmeruva.musicwiki.models.Album
import com.akashmeruva.musicwiki.models.Track
import com.akashmeruva.musicwiki.databinding.ActivityArtistInfoBinding
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide

class Artist_info_Activity : AppCompatActivity() {

    lateinit var genreadapter: Artist_InfoGenre_Recycler_view_Adapter
    lateinit var albumadapter: Artist_info_top_album_recyclerview_adapter
    lateinit var tracksadapter: Artist_info_top_tracks_recyclerview_adapter
    private lateinit var binding:ActivityArtistInfoBinding
    var artistName : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArtistInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

         artistName = intent.getStringExtra("artist_name")
        val imagelink = intent.getStringExtra("image_link")


        binding.artistInfoItemName.text = artistName
        Glide.with(this).load(imagelink).into(binding.artistInfoBcgImg)


        binding.artistInfoBackBtn.setOnClickListener {
            this.finish()
        }

        val manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.artistRecyclerView.layoutManager = manager


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
                binding.artistInfoTv2.text = summary.substringBefore("<a")

                val jsonobject3 = jsonObject.getJSONObject("stats")
                val playcount = jsonobject3.getString("playcount")
                val lizteners = jsonobject3.getString("listeners")

                binding.artistPlayerCount.text = "$playcount \n play count"
                binding.artistFollowers.text = "$lizteners \n followers"
            },
            {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            })

        this.let {
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest1)
        }

        genreadapter = Artist_InfoGenre_Recycler_view_Adapter(this)
        binding.artistRecyclerView.adapter = genreadapter

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



        val Album_manager = GridLayoutManager(this , 3)
        loadalbums()
        binding.artistAlbumsRecyclerView.layoutManager = Album_manager
        albumadapter = Artist_info_top_album_recyclerview_adapter(this)
        binding.artistAlbumsRecyclerView.adapter = albumadapter

        binding.artistAlbumsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        binding.artistTracksRecyclerView.layoutManager = TrackManager
        tracksadapter = Artist_info_top_tracks_recyclerview_adapter(this)
        binding.artistTracksRecyclerView.adapter = tracksadapter

        binding.artistTracksRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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