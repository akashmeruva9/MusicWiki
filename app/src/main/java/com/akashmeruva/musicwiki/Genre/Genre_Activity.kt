package com.akashmeruva.musicwiki.Genre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akashmeruva.musicwiki.GenreInfo.Genre_Info_Activity
import com.akashmeruva.musicwiki.R
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class Genre_Activity : AppCompatActivity() {

    lateinit var myadapter: GenreRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre)

        val manager = GridLayoutManager(this , 4)
        loadgenres()
        val RecyclerView = findViewById<RecyclerView>(R.id.genre_recycler_view)
        RecyclerView.layoutManager = manager
        myadapter = GenreRecyclerViewAdapter(this)
        RecyclerView.adapter = myadapter

        RecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!recyclerView.canScrollVertically(1)) {
                        loadgenres()

                }
            }
        })

        val btn = findViewById<LinearLayout>(R.id.close_slider_btn)
        val sliderLayout = findViewById<LinearLayout>(R.id.slider_layout)

        btn.setOnClickListener {
            sliderLayout.visibility = View.GONE
        }

    }
    fun loadgenres() {

        val url = "https://ws.audioscrobbler.com/2.0/?method=chart.gettoptags&api_key=2d27e848887a6c209a96fe02d7dc1f51&format=json"

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
    }

    fun onItemClicked(s: String) {

        val intent = Intent(this , Genre_Info_Activity::class.java)
        intent.putExtra("genre_name" , s)
        startActivity(intent)

    }
}