package com.example.twitterclone.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.twitterclone.AllTweetsFragment
import com.example.twitterclone.FollowersFragment
import com.example.twitterclone.HomeScreenActivity

class ViewPagerAdapter(activity:FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->FollowersFragment()
            else -> AllTweetsFragment()
        }
    }

}