package com.example.eataewon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eataewon.Adapter.HomeViewAdapter
import com.example.eataewon.connect.BbsDto

class HomeFragment: Fragment(R.layout.fragment_home) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // 테스트 데이터
        val testList = arrayListOf<BbsDto>(
            BbsDto("aaa", 1, "가장 사랑받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.cafe1, "#카페", "2022-03-22",
                "폰트커피", "서울 영등포구 경인로77가길 6 1층", 37.512218, 126.8925455,
                0, 0),
            BbsDto("aaa", 2, "가장 사랑받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.cafe2, "#카페", "2022-03-22",
                "프릳츠커피", "서울 영등포구 경인로77가길 6 1층", 37.512218, 126.8925455,
                0, 0),
            BbsDto("aaa", 3, "가장 사랑받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.cafe3, "#카페", "2022-03-22",
                "스타벅스", "서울 영등포구 경인로77가길 6 1층", 37.512218, 126.8925455,
                0, 0),
            BbsDto("aaa", 4, "가장 사랑받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.cafe4, "#카페", "2022-03-22",
                "투썸플레이스", "서울 영등포구 경인로77가길 6 1층", 37.512218, 126.8925455,
                0, 0),
            BbsDto("aaa", 5, "가장 사랑받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.cafe5, "#카페", "2022-03-22",
                "이디야커피", "서울 영등포구 경인로77가길 6 1층", 37.512218, 126.8925455,
                0, 0),
            BbsDto("aaa", 5, "가장 사랑받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.food1, "#카페", "2022-03-22",
                "이디야커피", "서울 영등포구 경인로77가길 6 1층", 37.512218, 126.8925455,
                0, 0)
        )

        // ViewPager 작성중
        /*viewPager.adapter = HomeViewAdapter(activity!!, testList)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL*/

        // 리사이클러뷰 db 데이터와 접함
        val homeViewAdapter = HomeViewAdapter(activity!!, testList)
        var homeRecyclerView1 = view.findViewById<RecyclerView>(R.id.HomeRecyclerView1)
        var homeRecyclerView2 = view.findViewById<RecyclerView>(R.id.HomeRecyclerView2)
        var homeRecyclerView3 = view.findViewById<RecyclerView>(R.id.HomeRecyclerView3)
        var homeRecyclerView4 = view.findViewById<RecyclerView>(R.id.HomeRecyclerView4)
        var homeRecyclerView5 = view.findViewById<RecyclerView>(R.id.HomeRecyclerView5)
        homeRecyclerView1.adapter = homeViewAdapter


        // Home 화면 최상단 RecyclerView Layout
        val layout1 = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        homeRecyclerView1.layoutManager = layout1
        homeRecyclerView1.setHasFixedSize(true)

        // Home 화면 두 번째 RecyclerView Layout
        val layout2 = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        homeRecyclerView2.layoutManager = layout2
        homeRecyclerView2.setHasFixedSize(true)

        // Home 화면 세 번째 RecyclerView Layout
        val layout3 = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        homeRecyclerView3.layoutManager = layout3
        homeRecyclerView3.setHasFixedSize(true)

     return view
    }

    
}