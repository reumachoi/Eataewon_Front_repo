package com.example.eattaewon;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.eattaewon.connect.MemberDto

import com.example.eattaewon.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity()/*, View.OnClickListener*/{

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //디테일 테스트 버튼 (최아름)
        binding.testBtn.setOnClickListener {
            val i = Intent(this, BbsDetailActivity::class.java)
            startActivity(i)
        }

        //백엔드 통신 확인용
        /*var result = MemberDao.getInstance().test()
        binding.loginID.setText(result.toString())*/
/*
        val loginBtn = findViewById<Button>(R.id.login_Btn)
        val signUpBtn = findViewById<Button>(R.id.signUpAtivity_Btn)
        val googleBtn = findViewById<Button>(R.id.google_Btn)
        val naverBtn = findViewById<Button>(R.id.naver_Btn)

        loginBtn.setOnClickListener(this)
        signUpBtn.setOnClickListener(this)
        googleBtn.setOnClickListener(this)
        naverBtn.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        val loginID = findViewById<TextView>(R.id.loginID)
        val loginPW = findViewById<TextView>(R.id.loginPw)

        when (view?.id) {

            R.id.login_Btn -> {
                val id = loginID.text.toString()
                val pw = loginPW.text.toString()
                val dto = MemberDto(0,"", id, pw, "", "", "", 0,"")
                val checkLogin = MemberDao.getInstance().login(dto)
                if (checkLogin != null) {
                    Toast.makeText(this, "환영합니다. ${checkLogin.id}님", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.signUpAtivity_Btn -> startActivity(Intent(this, SignActivity::class.java))


            R.id.google_Btn -> {

            }

            R.id.naver_Btn -> {

            }
        }*/
    }
}

