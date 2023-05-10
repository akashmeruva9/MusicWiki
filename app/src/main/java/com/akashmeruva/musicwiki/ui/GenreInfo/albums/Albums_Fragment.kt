package com.akashmeruva.musicwiki.ui.GenreInfo.albums

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akashmeruva.musicwiki.ui.GenreInfo.adapters.Genre.MySingleton
import com.akashmeruva.musicwiki.ui.GenreInfo.albums.album_info.AlbumInfoActivity
import com.akashmeruva.musicwiki.R
import com.akashmeruva.musicwiki.databinding.FragmentAlbumsBinding
import com.akashmeruva.musicwiki.models.Album
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import java.lang.Exception

class Albums_Fragment : Fragment(R.layout.fragment_albums_) {

    lateinit var myadapter: AlbumRecyclerViewAdapter
    var genreName :String? = null
    private lateinit var binding :FragmentAlbumsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAlbumsBinding.bind(view)
        genreName =  requireActivity().intent.getStringExtra("genre_name")
        val manager = GridLayoutManager(requireActivity() , 3)
        loadalbums()
        binding.albumRecyclerView.layoutManager = manager
        myadapter = AlbumRecyclerViewAdapter(this)
        binding.albumRecyclerView.adapter = myadapter

        binding.albumRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!recyclerView.canScrollVertically(1)) {
                    loadalbums()
                }
            }
        })
    }

    fun loadalbums() {

        try {
            val url =
                "https://ws.audioscrobbler.com/2.0/?method=tag.gettopalbums&tag=$genreName&api_key=2d27e848887a6c209a96fe02d7dc1f51&format=json"

            val jsonObjectRequest1 = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->

                    val jsonObject = response.getJSONObject("albums")
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

                        AlbumsArray.add(Album(AlbumName, ArtistName, image_link))
                    }
                    myadapter.update(AlbumsArray)
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

    fun onItemClicked(s: Album) {

        val intent = Intent(requireActivity() , AlbumInfoActivity::class.java)
        intent.putExtra("album_name" , s.name)
        intent.putExtra("artist_name" , s.artist)
        intent.putExtra("image_link" , s.image_link)
        startActivity(intent)

    }
}