package com.example.eattaewon_back_kimminki

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*

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