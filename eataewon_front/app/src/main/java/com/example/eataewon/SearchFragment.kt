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
import com.example.eataewon.connect.BbsDao
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
        val list = BbsDao.getInstance().getBbsListApp() as ArrayList<BbsDto>

        val bbsAdapter = SearchBbsAdapter(requireActivity(), list)
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


}