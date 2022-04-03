package com.example.eataewon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eataewon.Adapter.SearchBbsAdapter
import com.example.eataewon.connect.BbsDto

class SearchFragment: Fragment(R.layout.fragment_search) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val searchview = view.findViewById<SearchView>(R.id.SearchView)

        // 리사이클러뷰 db 데이터와 접함
        val bbsAdapter = SearchBbsAdapter(requireActivity(), testList)
        var recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = bbsAdapter

        // 리사이클러뷰 GridLayout으로 설정
        val layout = GridLayoutManager(activity, 2)
        recyclerView.layoutManager = layout
        recyclerView.setHasFixedSize(true)


        val mapBtn = view.findViewById<Button>(R.id.mapBtn)
        // 구글맵 뷰
        mapBtn.setOnClickListener {
            val mapsFragment = MapsFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.main_frame, mapsFragment)
            transaction.commit()
        }
        return view
    }

    // 검색기능 구현
    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val search = menu.findItem(R.id.SearchView)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    private  fun searchDatabase(query: String?) {
        val searchQuery = "%$query%"
    }*/

    companion object {
        // 테스트 데이터
        val testList = arrayListOf<BbsDto>(
            BbsDto(
                "aaa","닉네임1", 1, "가장 사랑받는 공간인데 두 줄 테스트를 위한 장문 작성을 시도 추가로 작성해야 두 줄로 보임", "플랫화이트가 맛있는 카페",
                R.drawable.cafe1, "#카페", "2022-03-22",
                "폰트커피", "서울 영등포구 경인로77가길 6 1층", "02-123-1234","https://naver.com",37.512218, 126.8925455,
                0, 0,""
            ),
            BbsDto(
                "bbb","닉네임2", 2, "두 번째로 사랑받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.cafe2, "#카페", "2022-03-22",
                "프릳츠커피", "서울 영등포구 경인로77가길 6 1층","02-123-1234","https://naver.com", 37.512218, 126.8925455,
                0, 0,""
            ),
            BbsDto(
                "ccc","닉네임3", 3, "세 번째로 사랑받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.cafe3, "#카페", "2022-03-22",
                "스타벅스", "서울 영등포구 경인로77가길 6 1층","02-123-1234","https://naver.com", 37.512218, 126.8925455,
                0, 0,""
            ),
            BbsDto(
                "ddd", "닉네임4",4, "네 번째로 사랑받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.cafe4, "#카페", "2022-03-22",
                "투썸플레이스", "서울 영등포구 경인로77가길 6 1층","02-123-1234","https://naver.com", 37.512218, 126.8925455,
                0, 0,""
            ),
            BbsDto(
                "eee","닉네임5", 5, "다섯 번째로 사랑받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.cafe5, "#카페", "2022-03-22",
                "이디야커피", "서울 영등포구 경인로77가길 6 1층","02-123-1234","https://naver.com", 37.512218, 126.8925455,
                0, 0,""
            ),
            BbsDto(
                "fff","닉네임6", 5, "가장 미움받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.food1, "#카페", "2022-03-22",
                "이디야커피", "서울 영등포구 경인로77가길 6 1층","02-123-1234","https://naver.com", 37.512218, 126.8925455,
                0, 0,""
            )
        )
    }
}