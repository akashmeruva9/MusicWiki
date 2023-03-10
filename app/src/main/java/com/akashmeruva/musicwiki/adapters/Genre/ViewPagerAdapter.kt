package com.akashmeruva.musicwiki.adapters.Genre

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter( fm: FragmentManager?, var behavior : Int) : FragmentPagerAdapter(fm!! , behavior) {

    private  var fragmentArrayList = ArrayList<Fragment>()
    private  var fragmentTitleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return fragmentArrayList.get(position)
    }

    override fun getCount(): Int {
        return fragmentArrayList.size
    }

   fun addFragment(fragment : Fragment , title : String) {

       fragmentArrayList.add(fragment)
       fragmentTitleList.add(title)
   }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitleList.get(position)
    }
}