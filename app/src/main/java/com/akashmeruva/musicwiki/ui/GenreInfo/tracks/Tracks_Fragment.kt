package com.akashmeruva.musicwiki.ui.GenreInfo.tracks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akashmeruva.musicwiki.adapters.Genre.MySingleton
import com.akashmeruva.musicwiki.R
import com.akashmeruva.musicwiki.databinding.FragmentTracksBinding
import com.akashmeruva.musicwiki.models.Track
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import java.lang.Exception


class Tracks_Fragment : Fragment(R.layout.fragment_tracks_) {

    lateinit var myadapter: TracksRecyclerViewAdapter
    var genreName :String? = null
    private lateinit var binding: FragmentTracksBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTracksBinding.bind(view)

        genreName =  requireActivity().intent.getStringExtra("genre_name")
        val manager = GridLayoutManager(requireActivity() , 3)
        loadtracks()
        binding.tracksRecyclerView.layoutManager = manager
        myadapter = TracksRecyclerViewAdapter(this)
        binding.tracksRecyclerView.adapter = myadapter

        binding.tracksRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!recyclerView.canScrollVertically(1)) {
                    loadtracks()
                }
            }
        })
    }

    fun loadtracks() {

        try {
            val url =
                "https://ws.audioscrobbler.com/2.0/?method=tag.gettoptracks&tag=$genreName&api_key=2d27e848887a6c209a96fe02d7dc1f51&format=json"

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

                        tracksArray.add(Track(ArtistName, image_link))
                    }
                    myadapter.update(tracksArray)

                },
                {
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG)
                        .show()
                })

            this.let {
                MySingleton.getInstance(requireContext()).addToRequestQueue(jsonObjectRequest1)
            }
        }catch( e : Exception)
        {
            Log.d("EXC" , e.message.toString())
        }
    }


}