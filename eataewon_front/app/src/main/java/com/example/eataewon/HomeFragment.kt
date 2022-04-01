package com.example.eataewon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.eataewon.Adapter.BbsPagerAdapter
import com.example.eataewon.Adapter.HomePagerAdapter
import com.example.eataewon.connect.BbsDto
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // RecyclerView.Adapter<ViewHolder>()
        CurationPager.adapter = HomePagerAdapter(testList)
        // ViewPager Paging 방향은 Horizontal
        CurationPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // HOME BBS VIEW
        BbsPager.adapter = BbsPagerAdapter(testList)
        BbsPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        //HomeBbsRecyclerView.adapter = BbsPagerAdapter(testList)


        /*CurationPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            // Paging 완료되면 호출
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })*/
    }

    companion object {
        // 테스트 데이터
        val testList = arrayListOf<BbsDto>(
            BbsDto(
                "aaa", 1, "가장 사랑받는 공간인데 두 줄 테스트를 위한 장문 작성을 시도 추가로 작성해야 두 줄로 보임", "플랫화이트가 맛있는 카페",
                R.drawable.cafe1, "#카페", "2022-03-22",
                "폰트커피", "서울 영등포구 경인로77가길 6 1층", 37.512218, 126.8925455,
                0, 0
            ),
            BbsDto(
                "bbb", 2, "두 번째로 사랑받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.cafe2, "#카페", "2022-03-22",
                "프릳츠커피", "서울 영등포구 경인로77가길 6 1층", 37.512218, 126.8925455,
                0, 0
            ),
            BbsDto(
                "ccc", 3, "세 번째로 사랑받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.cafe3, "#카페", "2022-03-22",
                "스타벅스", "서울 영등포구 경인로77가길 6 1층", 37.512218, 126.8925455,
                0, 0
            ),
            BbsDto(
                "ddd", 4, "네 번째로 사랑받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.cafe4, "#카페", "2022-03-22",
                "투썸플레이스", "서울 영등포구 경인로77가길 6 1층", 37.512218, 126.8925455,
                0, 0
            ),
            BbsDto(
                "eee", 5, "다섯 번째로 사랑받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.cafe5, "#카페", "2022-03-22",
                "이디야커피", "서울 영등포구 경인로77가길 6 1층", 37.512218, 126.8925455,
                0, 0
            ),
            BbsDto(
                "fff", 5, "가장 미움받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.food1, "#카페", "2022-03-22",
                "이디야커피", "서울 영등포구 경인로77가길 6 1층", 37.512218, 126.8925455,
                0, 0
            )
        )
    }
}