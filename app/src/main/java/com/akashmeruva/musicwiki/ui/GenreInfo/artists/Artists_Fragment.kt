package com.akashmeruva.musicwiki.ui.GenreInfo.artists

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akashmeruva.musicwiki.adapters.MySingleton
import com.akashmeruva.musicwiki.ui.GenreInfo.artists.artist_info.Artist_info_Activity
import com.akashmeruva.musicwiki.R
import com.akashmeruva.musicwiki.databinding.FragmentArtistsBinding
import com.akashmeruva.musicwiki.models.Artist
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class Artists_Fragment : Fragment(R.layout.fragment_artists_) {

    lateinit var myadapter: ArtistsRecyckerViewAdapter
    var genreName :String? = null
    private lateinit var binding : FragmentArtistsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentArtistsBinding.bind(view)

        genreName =  requireActivity().intent.getStringExtra("genre_name")
        val manager = GridLayoutManager(requireActivity() , 3)
        loadartists()
        binding.artistsRecyclerView.layoutManager = manager
        myadapter = ArtistsRecyckerViewAdapter(this)
        binding.artistsRecyclerView.adapter = myadapter

        binding.artistsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!recyclerView.canScrollVertically(1)) {
                    loadartists()
                }
            }
        })

    }

    fun loadartists() {

        val url = "https://ws.audioscrobbler.com/2.0/?method=tag.gettopartists&tag=$genreName&api_key=2d27e848887a6c209a96fe02d7dc1f51&format=json"

        val jsonObjectRequest1 = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                val jsonObject = response.getJSONObject("topartists")
                val jsonArray = jsonObject.getJSONArray("artist")

                val artistsArray = ArrayList<Artist>()

                for (i in 0 until jsonArray.length()) {
                    val JsonObject1 = jsonArray.getJSONObject(i)
                    val ArtistName = JsonObject1.getString("name")
                    val jsonArray3 = JsonObject1.getJSONArray("image")
                    val jsonobject4 = jsonArray3.getJSONObject(3)
                    val image_link = jsonobject4.getString("#text")

                    artistsArray.add(Artist(ArtistName , image_link))
                }
                myadapter.update(artistsArray)
            },
            {
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG).show()
            })

        this.let {
            MySingleton.getInstance(requireContext()).addToRequestQueue(jsonObjectRequest1)
        }
    }

    fun onItemClicked(s: Artist) {
        val intent = Intent(requireActivity() , Artist_info_Activity::class.java)
        intent.putExtra("artist_name" , s.name)
        intent.putExtra("image_link" , s.image_link)
        startActivity(intent)
    }
}