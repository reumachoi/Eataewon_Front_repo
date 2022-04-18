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
import com.example.eataewon.connect.BbsDto


class mypageGetBbsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mypage_get_bbs,container,false)

        val myPageUser = arguments?.getString("mypageuser")

        val myBbsList = myPageUser?.let { BbsDao.getInstance().findMyBbs(it) }
        println(myBbsList.toString())

        if(myBbsList!=null){
            val bbsAdapter = (myBbsList as ArrayList<BbsDto>?)?.let { MypageBbsAdapter(requireActivity(), it) }
            var recyclerView = view?.findViewById<RecyclerView>(R.id.mypageBbsRecycler)

            recyclerView?.layoutManager = GridLayoutManager(activity, 2)
            recyclerView?.adapter = bbsAdapter
            recyclerView?.setHasFixedSize(true)
        }else{
            //작성한 글이 없습니다
        }





        return view
    }


}