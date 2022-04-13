package com.example.eataewon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eataewon.Adapter.SearchBbsAdapter
import com.example.eataewon.connect.BbsDao
import com.example.eataewon.connect.BbsDto
import com.example.eataewon.databinding.ActivitySearchBinding
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity() {

    val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val recyclerView = findViewById<RecyclerView>(R.id.searchRecyclerView)

        // 리사이클러뷰 db 데이터와 접함
        var list = BbsDao.getInstance().getSearchListApp() as ArrayList<BbsDto>

        val searchAdapter = SearchBbsAdapter(this, list)
        recyclerView.adapter = searchAdapter

        // 리사이클러뷰 GridLayout으로 설정
        val layout = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layout
        recyclerView.setHasFixedSize(true)

        binding.searchRecyclerView.adapter = searchAdapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            // 입력을 완료 후에 검색해야 검색 목록이 보여짐
            override fun onQueryTextSubmit(query: String?): Boolean {
                list.clear()
                val searchText = query!!.toLowerCase(Locale.getDefault())
                println("~~~~~~~~~~~~~~~~${searchText}~~~~~~~~~~~~~~~~~")
                if (searchText!=""){

                    println("~~~~~~~~~~~~~~if로 들어온 ${searchText}~~~~~~~~~~~~~~~~~")
                    val searchList = BbsDao.getInstance().getBbsListSearchApp(searchText)
                    println("~~~~~~~~~~~~~~~${searchList}~~~~~~~~~~~~~~~~")
                    searchList?.let { list.addAll(it) }

                    recyclerView.adapter!!.notifyDataSetChanged()
                } else {
                    println("~~~~~~~~~~~~~~else로 들어온 ${searchText}~~~~~~~~~~~~~~~~~")

                    list.clear()
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }
            // 검색어를 작성중에 해당 검색어에 맞는 목록이 있다면 보여짐
            override fun onQueryTextChange(newText: String?): Boolean {
                list.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isEmpty()){

                    println("~~~~~~~~~~~~~~if로 들어온 ${searchText}~~~~~~~~~~~~~~~~~")
                    val searchList = BbsDao.getInstance().getBbsListSearchApp(searchText)
                    println("~~~~~~~~~~~~~~~${searchList}~~~~~~~~~~~~~~~~")
                    searchList?.let { list.addAll(it) }
                    recyclerView.adapter!!.notifyDataSetChanged()
                } else {
                    list.clear()
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })
    }
}
