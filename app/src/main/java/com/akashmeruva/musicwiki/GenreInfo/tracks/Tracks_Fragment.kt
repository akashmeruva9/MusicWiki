package com.akashmeruva.musicwiki.GenreInfo.tracks

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akashmeruva.musicwiki.Genre.MySingleton
import com.akashmeruva.musicwiki.GenreInfo.albums.album_info.Album_info_Activity
import com.akashmeruva.musicwiki.GenreInfo.artists.Artist
import com.akashmeruva.musicwiki.GenreInfo.artists.artist_info.Artist_info_Activity
import com.akashmeruva.musicwiki.R
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest


class Tracks_Fragment : Fragment(R.layout.fragment_tracks_) {

    lateinit var myadapter: TracksRecyclerViewAdapter
    var genreName :String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        genreName =  requireActivity().intent.getStringExtra("genre_name")
        val manager = GridLayoutManager(requireActivity() , 3)
        loadtracks()
        val RecyclerView = requireActivity().findViewById<RecyclerView>(R.id.tracks_recycler_view)
        RecyclerView.layoutManager = manager
        myadapter = TracksRecyclerViewAdapter(this)
        RecyclerView.adapter = myadapter

        RecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!recyclerView.canScrollVertically(1)) {
                    loadtracks()
                }
            }
        })
    }

    fun loadtracks() {

        val url = "https://ws.audioscrobbler.com/2.0/?method=tag.gettoptracks&tag=$genreName&api_key=2d27e848887a6c209a96fe02d7dc1f51&format=json"

        val jsonObjectRequest1 = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                val jsonObject = response.getJSONObject("tracks")
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
                myadapter.update(tracksArray)

            },
            {
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG).show()
            })

        this.let {
            MySingleton.getInstance(requireContext()).addToRequestQueue(jsonObjectRequest1)
        }
    }


}