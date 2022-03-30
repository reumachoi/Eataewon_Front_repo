package com.example.eataewon

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eataewon.connect.MapSearchListDto
import com.example.eataewon.databinding.ActivityWriteBinding

class WriteActivity : AppCompatActivity() {

    val binding by lazy { ActivityWriteBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val findMap = findViewById<Button>(R.id.write_addressBtn)

        findMap.setOnClickListener {
            var i = Intent(this, SearchKakaoMapActivity::class.java)
            startActivity(i)
        }

        val searchData = intent.getParcelableExtra<MapSearchListDto>("shopData")

        println(searchData.toString())
        /*
        shopname = searchData.name
        address = searchData.road
        latitude = searchData.y
        longitude = searchData.x
        shopphnum = searchData.phone
        shopurl = searchData.place_url
        */

    }
}