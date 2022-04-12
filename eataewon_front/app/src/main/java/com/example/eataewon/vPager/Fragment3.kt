package com.example.eataewon.vPager

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eataewon.R
import kotlinx.android.synthetic.main.fragment_1.view.*

class Fragment3 : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_3, container, false)

        return view
    }

    fun setImg(uri:String){
        view?.imageView?.setImageURI(Uri.parse(uri))
    }
}