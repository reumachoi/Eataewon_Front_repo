package com.example.eataewon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.eataewon.connect.MemberDto
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.eataewon.connect.MemberDto
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val writeActivity = WriteActivity()
        val bookmarkFragment = BookmarkFragment()
        val mypageFragment = MypageFragment(this)
        val intent = intent
        val user = intent.getParcelableExtra<MemberDto>("user")
        setCurrentFragment(homeFragment)

        var i = Intent(this,WriteActivity::class.java)


        //bottomNavi.itemRippleColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
        bottomNavi.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.action_home->setCurrentFragment(homeFragment)
                R.id.action_search->setCurrentFragment(searchFragment)
                R.id.action_write->{
                    //안도현(로그인 후 홈엑티비티로 넘어가면서 intent.put으로 user값 넘기기)
                    val intent = Intent(this,WriteActivity::class.java)
                    intent.putExtra("user",user)
                    startActivity(intent)
                }
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