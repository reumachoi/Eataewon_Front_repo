package com.example.eataewon.vPager

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.eataewon.R
import kotlinx.android.synthetic.main.fragment_1.view.*
import kotlinx.android.synthetic.main.fragment_3.*


class Fragment3(var uri:String) : Fragment() {

    var v : View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_3, container, false)

        setImg(uri)

        return v

    }

    fun setImg(uri:String){
        var dePic3 = v?.findViewById<ImageView>(R.id.dePic3)

        dePic3?.setImageURI(Uri.parse(uri))
    }
}