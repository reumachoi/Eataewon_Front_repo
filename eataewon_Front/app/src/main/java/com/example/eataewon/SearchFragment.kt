package com.example.eataewon

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.eataewon.R

import kotlinx.android.synthetic.main.fragment_search.*

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