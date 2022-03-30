package com.example.eataewon

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val writeFragment = WriteFragment()
        val bookmarkFragment = BookmarkFragment()
        val mypageFragment = MypageFragment()

        setCurrentFragment(homeFragment)


        //bottomNavi.itemRippleColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
        bottomNavi.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.action_home->setCurrentFragment(homeFragment)
                R.id.action_search->setCurrentFragment(searchFragment)
                R.id.action_write->setCurrentFragment(writeFragment)
                R.id.action_bookmark->setCurrentFragment(bookmarkFragment)
                R.id.action_mypage->setCurrentFragment(mypageFragment)
            }
            true
        }
    }

    fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_frame, fragment)
            commit()
        }
}