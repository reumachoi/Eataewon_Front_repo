package com.example.eataewon

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
<<<<<<< Updated upstream:eataewon_Front/app/src/main/java/com/example/eataewon/SearchFragment.kt
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eataewon.Adapter.BbsAdapter
import com.example.eataewon.connect.BbsDto
=======
import kotlinx.android.synthetic.main.fragment_search.*
>>>>>>> Stashed changes:eataewon_front/app/src/main/java/com/example/eattaewon/SearchFragment.kt

class SearchFragment: Fragment(R.layout.fragment_search) {

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        //return super.onCreateView(inflater, container, savedInstanceState)
//        return inflater.inflate(R.layout.activity_search, container, false)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val mapsFragment = MapsFragment()

        mapBtn.setOnClickListener {

        }

    }
}