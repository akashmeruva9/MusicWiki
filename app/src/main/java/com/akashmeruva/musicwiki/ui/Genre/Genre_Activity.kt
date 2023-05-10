package com.akashmeruva.musicwiki.ui.Genre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akashmeruva.musicwiki.ui.GenreInfo.adapters.Genre.MySingleton
import com.akashmeruva.musicwiki.databinding.ActivityGenreBinding
import com.akashmeruva.musicwiki.ui.GenreInfo.Genre_Info_Activity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import java.lang.Exception

class Genre_Activity : AppCompatActivity() {

    lateinit var myadapter: GenreRecyclerViewAdapter
    private lateinit var binding : ActivityGenreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGenreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val manager = GridLayoutManager(this , 4)
        loadgenres()
        binding.genreRecyclerView.layoutManager = manager
        myadapter = GenreRecyclerViewAdapter(this)
        binding.genreRecyclerView.adapter = myadapter

        binding.genreRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!recyclerView.canScrollVertically(1)) {
                        loadgenres()
                }
            }
        })

        binding.closeSliderBtn.setOnClickListener {
            binding.sliderLayout.visibility = View.GONE
        }

    }
    fun loadgenres() {

        try {
            val url =
                "https://ws.audioscrobbler.com/2.0/?method=chart.gettoptags&api_key=2d27e848887a6c209a96fe02d7dc1f51&format=json"

            val jsonObjectRequest1 = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->

                    val jsonObject = response.getJSONObject("tags")
                    val jsonArray = jsonObject.getJSONArray("tag")

                    val genreArray = ArrayList<String>()
                    for (i in 0 until jsonArray.length()) {
                        val JsonObject1 = jsonArray.getJSONObject(i)
                        val temp = JsonObject1.getString("name")
                        genreArray.add(temp)
                    }
                    myadapter.update(genreArray)
                },
                {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
                })

            this.let {
                MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest1)
            }
        }catch( e : Exception)
        {
            Log.d("EXC" , e.message.toString())
        }
    }

    fun onItemClicked(s: String) {

        val intent = Intent(this , Genre_Info_Activity::class.java)
        intent.putExtra("genre_name" , s)
        startActivity(intent)

    }
}