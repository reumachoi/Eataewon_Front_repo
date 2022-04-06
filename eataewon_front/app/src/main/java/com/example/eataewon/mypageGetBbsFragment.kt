package com.example.eataewon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eataewon.Adapter.MypageBbsAdapter
import com.example.eataewon.connect.BbsDao


class mypageGetBbsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val mypageUser = arguments?.getString("mypageuser")

        val myBbsList = BbsDao.getInstance().findMyBbs(mypageUser!!)
        println(myBbsList.toString())
/*
        // 리사이클러뷰 db 데이터와 접함
        val bbsAdapter = MypageBbsAdapter(requireActivity(), MypageFragment.testList)
        var recyclerView = view?.findViewById<RecyclerView>(R.id.mypageBbsRecycler)
        recyclerView?.adapter = bbsAdapter

        // 리사이클러뷰 GridLayout으로 설정
        val layout = GridLayoutManager(activity, 2)
        recyclerView?.layoutManager = layout
        recyclerView?.setHasFixedSize(true)

*/
        return inflater.inflate(R.layout.fragment_mypage_get_bbs, container, false)
    }


}