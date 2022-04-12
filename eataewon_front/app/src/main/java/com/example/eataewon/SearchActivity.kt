package com.example.eataewon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eataewon.Adapter.SearchBbsAdapter
import com.example.eataewon.connect.BbsDao
import com.example.eataewon.connect.BbsDto
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity() {

    lateinit var searchRecyclerView: RecyclerView
    lateinit var searchBbsAdapter: SearchBbsAdapter

    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        //val searchView = findViewById<SearchView>(R.id.SearchView)
        //val recyclerView = findViewById<RecyclerView>(R.id.searchRecyclerView)

        // 리사이클러뷰 db 데이터와 접함
        val list = BbsDao.getInstance().getSearchListApp() as ArrayList<BbsDto>

        searchRecyclerView = findViewById(R.id.searchRecyclerView)
        searchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(searchViewTextListener)

        searchRecyclerView.layoutManager = GridLayoutManager(this, 2)
        searchBbsAdapter = SearchBbsAdapter(this, list)
        searchRecyclerView.adapter = searchBbsAdapter

        //val bbsAdapter = SearchBbsAdapter(this, list)
        //recyclerView.adapter = bbsAdapter

        // 리사이클러뷰 GridLayout으로 설정
        //val layout = GridLayoutManager(this, 2)
        //recyclerView.layoutManager = layout
        //recyclerView.setHasFixedSize(true)
    }

    // SearchView 텍스트 입력시 이벤트
    var searchViewTextListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            //텍스트 입력/수정시에 호출
            override fun onQueryTextChange(s: String): Boolean {
                searchBbsAdapter.filter.filter(s)

                return false
            }
        }
}
