package com.example.eattaewon;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
<<<<<<< HEAD:eataewon_Front/app/src/main/java/com/example/eataewon/MainActivity.kt
<<<<<<< Updated upstream:eataewon_Front/app/src/main/java/com/example/eataewon/MainActivity.kt
import com.example.eataewon.connect.MemberDao
import com.example.eataewon.connect.MemberDto
=======
import com.example.eattaewon.connect.MemberDao
import com.example.eattaewon.connect.MemberDto
import com.example.eattaewon.connect.RetrofitClient
>>>>>>> parent of 2c66f7a (0328 18:17 (프로젝트,패키지명 변경)):eataewon_Front/app/src/main/java/com/example/eattaewon/MainActivity.kt

import com.example.eattaewon.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
=======
import com.example.eattaewon.connect.MemberDto
import com.example.eattaewon.connect.RetrofitClient

import com.example.eattaewon.databinding.ActivityMainBinding
>>>>>>> Stashed changes:eataewon_front/app/src/main/java/com/example/eattaewon/MainActivity.kt

class MainActivity : AppCompatActivity(),View.OnClickListener{

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
                val dto = MemberDto(id, "", pw, "", "",0, 0,"")

                /*val checkLogin = MemberDao.getInstance().login(dto)
                if (checkLogin != null) {
                    Toast.makeText(this, "환영합니다. ${checkLogin.id}님", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }*/

                //백엔드 접속후 id pw값을 찾아 login값에 dto값 넣기
                val login = MemberDao.getInstance().login(dto)
                if (login != null) {
                    Toast.makeText(this, "환영합니다. ${login.id}님", Toast.LENGTH_SHORT).show()

                    //안도현(로그인 후 홈엑티비티로 넘어가면서 intent.put으로 login값 넘기기)
                    val intent = Intent(this,HomeActivity::class.java)
                    intent.putExtra("user",login)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.signUpAtivity_Btn -> startActivity(Intent(this, SignActivity::class.java))


            R.id.google_Btn -> {

            }

            R.id.naver_Btn -> {

            }
        }
    }
}

