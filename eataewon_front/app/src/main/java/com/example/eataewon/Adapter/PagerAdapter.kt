package com.example.eataewon.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fa:FragmentActivity): FragmentStateAdapter(fa) {

    var fList = listOf<Fragment>()

    override fun getItemCount(): Int {
       return fList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fList.get(position)
    }
}