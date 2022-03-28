package com.example.eataewon

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eataewon.Adapter.BbsAdapter
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

        // 테스트 데이터
        val testList = arrayListOf<BbsDto>(
            BbsDto("aaa", 1, "가장 사랑받는 공간", "플랫화이트가 맛있는 카페", R.drawable.cafe1, "#카페", "2022-03-22",
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

        // 리사이클러뷰 db 데이터와 접함
        val bbsAdapter = BbsAdapter(activity!!, testList)
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
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
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


}