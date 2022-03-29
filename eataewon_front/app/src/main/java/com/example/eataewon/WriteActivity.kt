package com.example.eataewon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eataewon.connect.MapSearchListDto
import com.example.eataewon.databinding.ActivityWriteBinding

class WriteActivity : AppCompatActivity() {

    val binding by lazy { ActivityWriteBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.findShop.setOnClickListener {
            var i = Intent(this,SearchKakaoMapActivity::class.java)
            startActivity(i)
        }

        val searchData = intent.getParcelableExtra<MapSearchListDto>("shopData")

        binding.shopNameT.text = searchData?.name
        binding.shopLocaT.text = searchData?.road

        /*shopname = searchData.name
        address = searchData.road
        latitude = searchData.y
        longitude = searchData.x*/
    }
}