package com.example.eataewon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eataewon.Adapter.BookmarkBbsAdapter
import com.example.eataewon.Adapter.checkboxData
import com.example.eataewon.connect.BbsDao
import com.example.eataewon.connect.BbsDto
import com.example.eataewon.connect.MemberDto
import com.example.eataewon.connect.ScrapDto
import kotlinx.android.synthetic.main.fragment_bookmark.view.*
import java.util.*
import kotlin.collections.ArrayList


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

        val bookAdapter = BookmarkBbsAdapter(requireActivity(), bookmarkList as ArrayList<BbsDto>)

        var recyclerView = view?.findViewById<RecyclerView>(R.id.bookmarkRecycler)

        if (bookmarkList != null) {
            recyclerView?.layoutManager = GridLayoutManager(activity, 2)
            recyclerView?.adapter = bookAdapter
            recyclerView?.setHasFixedSize(true)
        }

        val showBtn = view?.findViewById<Button>(R.id.showBtn)
        val delBtn = view?.findViewById<Button>(R.id.delBtn)

        view.showBtn.setOnClickListener {
            val checkBtn = recyclerView?.findViewById<CheckBox>(R.id.bm_checkBtn)

            //두번째부터는 여기서 시작하는듯??
            //checkBtn?.isVisible = true

           /* delBtn?.isVisible = true
            showBtn?.isVisible = false*/

            //처음에는 숫자로 시작하고

            bookAdapter.updateRadioBtn(1)
            bookAdapter.notifyDataSetChanged()
        }

        view.delBtn.setOnClickListener {
            val checkBtn = recyclerView?.findViewById<CheckBox>(R.id.bm_checkBtn)
            //checkBtn?.isVisible = false

           /* showBtn?.isVisible = true
            delBtn?.isVisible = false*/

            bookAdapter.updateRadioBtn(0)
            bookAdapter.notifyDataSetChanged()

            val list:List<checkboxData> = bookAdapter.checkboxResult()

            val listDto = arrayListOf<ScrapDto>()

            for (i in list){
                if(i.checked){
                    println("${i.seq} seqseq~~~~")
                    val id = user.id
                    val bbsseq = i.seq
                    listDto.add(ScrapDto(id,bbsseq,0))
                }
            }
            println("${listDto}!!!!!")


            val result = BbsDao.getInstance().scrapDelete(listDto!!)

            if(result==true){
                println("스크랩 취소가 완료되었습니다")

                bookmarkList = BbsDao.getInstance().findBookmark(user?.id!!)
                bookAdapter.setData(bookmarkList as ArrayList<BbsDto>)
                getFragmentManager()?.let { it1 -> refreshFragment(this, it1) }
            }else{
                println("스크랩 취소를 실패했습니다")
            }


        }



        return view
    }

    fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager) {
        var ft: FragmentTransaction = fragmentManager.beginTransaction()
        ft.detach(fragment).attach(fragment).commit()
    }


}