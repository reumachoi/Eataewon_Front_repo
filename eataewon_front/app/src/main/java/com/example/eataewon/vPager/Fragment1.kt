
package com.example.eataewon.vPager

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.eataewon.R
import kotlinx.android.synthetic.main.fragment_1.*
import kotlinx.android.synthetic.main.fragment_1.view.*

class Fragment1(var uri:String) : Fragment() {

    var v : View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_1, container, false)
        println("!!!!!${uri}!!!!")
        setImg(uri)

        return v
    }

    fun setImg(uri:String){
        var dePic1 = v?.findViewById<ImageView>(R.id.dePic1)
        if(dePic1==null){
            println("depic1 is null~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
        }
        dePic1?.setImageURI(Uri.parse(uri))
    }
}