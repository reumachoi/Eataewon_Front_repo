package com.example.eattaewon_back_kimminki

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.eattaewon_back_kimminki.R


class SignActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_main)

        val signup = findViewById<Button>(R.id.signup)

        val id = findViewById<EditText>(R.id.id)
        val pw = findViewById<EditText>(R.id.pw)
        val email = findViewById<EditText>(R.id.email)
        val nickname = findViewById<EditText>(R.id.nickName)


    }
}




