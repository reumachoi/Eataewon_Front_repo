package com.example.eataewon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.eataewon.Adapter.HomeLowerAdapter
import com.example.eataewon.Adapter.HomeUpperAdapter
import com.example.eataewon.connect.BbsDao
import com.example.eataewon.connect.BbsDto
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Home Lower BBS Data View
        val list = BbsDao.getInstance().getLowerBbsList()

        val lowerAdapter = HomeLowerAdapter(requireActivity(), list!!)
        var lowerRecyclerView = view.findViewById<RecyclerView>(R.id.HomeLowerRecyclerView)
        lowerRecyclerView.adapter = lowerAdapter
        val layout = LinearLayoutManager(activity)
        lowerRecyclerView.layoutManager = layout
        lowerRecyclerView.setHasFixedSize(true)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val list = BbsDao.getInstance().getUpperBbsList()

        // RecyclerView.Adapter<ViewHolder>()
        CurationPager.adapter = HomeUpperAdapter(requireActivity(), list!!)
        // ViewPager Paging 방향은 Horizontal
        CurationPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }
}
