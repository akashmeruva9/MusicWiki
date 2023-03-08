package com.akashmeruva.musicwiki.GenreInfo

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.akashmeruva.musicwiki.Genre.MySingleton
import com.akashmeruva.musicwiki.GenreInfo.albums.Albums_Fragment
import com.akashmeruva.musicwiki.GenreInfo.artists.Artists_Fragment
import com.akashmeruva.musicwiki.GenreInfo.tracks.Tracks_Fragment
import com.akashmeruva.musicwiki.R
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.tabs.TabLayout

class Genre_Info_Activity : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre_info)

        val genreName = intent.getStringExtra("genre_name")
        val url = "https://ws.audioscrobbler.com/2.0/?method=tag.getinfo&tag=$genreName&api_key=2d27e848887a6c209a96fe02d7dc1f51&format=json"
        val nameTextView = findViewById<TextView>(R.id.genre_info_tv1)
        val descTextView = findViewById<TextView>(R.id.genre_info_tv2)
        val jsonObjectRequest1 = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                val jsonObject = response.getJSONObject("tag")
                val wikijsonObject = jsonObject.getJSONObject("wiki")
                val name = jsonObject.getString("name")
                var summary = wikijsonObject.getString("summary")
                summary = summary.substringBefore("<a")

                nameTextView.text = name
                descTextView.text = summary

            },
            {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            })

        this.let {
            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest1)
        }

        tabLayout = findViewById<View>(R.id.tabLayout) as TabLayout
        viewPager = findViewById<View>(R.id.viewpager) as ViewPager

        tabLayout!!.setupWithViewPager(viewPager)

        val VRadapter = ViewPagerAdapter( supportFragmentManager , FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        VRadapter.addFragment(Albums_Fragment() , "Albums")
        VRadapter.addFragment(Artists_Fragment() , "Artists")
        VRadapter.addFragment(Tracks_Fragment() , "Tracks")

        viewPager!!.adapter = VRadapter


        val backBtn = findViewById<ImageView>(R.id.genre_info_back_btn)
        backBtn.setOnClickListener {
            this.finish()
        }

    }
}