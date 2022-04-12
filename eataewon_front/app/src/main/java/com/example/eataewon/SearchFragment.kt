package com.example.eataewon

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eataewon.Adapter.SearchBbsAdapter
import com.example.eataewon.connect.BbsDao
import com.example.eataewon.connect.BbsDto
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_maps.*
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment: Fragment(R.layout.fragment_search) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //return super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        // 리사이클러뷰 db 데이터와 접함
        val list = BbsDao.getInstance().getSearchListApp() as ArrayList<BbsDto>

        val bbsAdapter = SearchBbsAdapter(requireActivity(), list)
        var recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = bbsAdapter

        // 리사이클러뷰 GridLayout으로 설정
        val layout = GridLayoutManager(activity, 2)
        recyclerView.layoutManager = layout
        recyclerView.setHasFixedSize(true)

        // SearchActivity로 이동하는 검색 버튼
        val searchBtn = view.findViewById<Button>(R.id.SearchBtn)
        searchBtn.setOnClickListener {

           val i = Intent(context, SearchActivity::class.java)
            startActivity(i)
        }

        val mapBtn = view.findViewById<Button>(R.id.mapBtn)
        // 구글맵 뷰
        mapBtn.setOnClickListener {
            val mapsFragment = MapsFragment(requireActivity())
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.main_frame, mapsFragment)
            transaction.commit()
        }
        return view
    }
}
