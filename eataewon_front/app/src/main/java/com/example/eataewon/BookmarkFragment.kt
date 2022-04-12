package com.example.eataewon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.eataewon.Adapter.BookmarkBbsAdapter
import com.example.eataewon.Adapter.MypageBbsAdapter
import com.example.eataewon.connect.BbsDao
import com.example.eataewon.connect.BbsDto
import com.example.eataewon.connect.MemberDao
import com.example.eataewon.connect.MemberDto
import kotlinx.android.synthetic.main.fragment_bookmark.*


class BookmarkFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bookmark, container, false)

        //임시 테스트
        val user = arguments?.getParcelable<MemberDto>("user")

        println("로그인아이디 ${user?.id}")
        var bookmarkList = BbsDao.getInstance().findBookmark(user?.id!!)
        println("북마크 페이지 : ${bookmarkList.toString()}")

        if (bookmarkList != null) {
            val bookAdapter = BookmarkBbsAdapter(requireActivity(), bookmarkList as ArrayList<BbsDto>)

            var recyclerView = view?.findViewById<RecyclerView>(R.id.bookmarkRecycler)

            recyclerView?.layoutManager = GridLayoutManager(activity, 2)
            recyclerView?.adapter = bookAdapter
            recyclerView?.setHasFixedSize(true)
        }else{

        }

        return view
    }

}