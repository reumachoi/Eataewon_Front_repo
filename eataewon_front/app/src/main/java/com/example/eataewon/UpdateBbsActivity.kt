package com.example.eataewon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eataewon.connect.BbsDto

class UpdateBbsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_bbs)

        val data = intent.getParcelableExtra<BbsDto>("passUpdate")
        println(data)
    }
}