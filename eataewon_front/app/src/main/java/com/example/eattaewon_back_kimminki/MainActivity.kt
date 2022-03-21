package com.example.eattaewon_back_kimminki;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.eattaewon_back_kimminki.connect.MemberDto

import com.example.eattaewon_back_kimminki.R
import com.example.eattaewon_back_kimminki.SignActivity
import com.example.eattaewon_back_kimminki.databinding.ActivityBbsDetailBinding
import com.example.eattaewon_back_kimminki.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ReadyService{

@GET("/base")
fun base(): Call<String>


    // 2. 문자열 보내고 받기
    @GET("/connParamGet")
    fun connParamGet(@Query("title") title:String): retrofit2.Call<String>
}




class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val loginBtn = findViewById<Button>(R.id.login_Btn)
        val signUpBtn = findViewById<Button>(R.id.signUp_Btn)
        val googleBtn = findViewById<Button>(R.id.google_Btn)
        val naverBtn = findViewById<Button>(R.id.naver_Btn)

        loginBtn.setOnClickListener(this)
        signUpBtn.setOnClickListener(this)
        googleBtn.setOnClickListener(this)
        naverBtn.setOnClickListener(this)
        
        /*  // Network 처리에 추가한다  == HttpURLConnection
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        // Gson 은 Java 객체를 JSON 으로 변환할 수 있다
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.139:3000/") // ip 주소 반드시 넣어야.
            .addConverterFactory(GsonConverterFactory.create(gson))     // object, integer
            .addConverterFactory(ScalarsConverterFactory.create())      // 문자열 리턴받는 경우
            .build()

        val service = retrofit.create(ReadyService::class.java)

        // 1. 문자열 받기
        //val call = service.base()


        // 2. 문자열 보내고 받기
        val call = service.connParamGet("제목입니다")


        val response = call.execute()
        if (response.isSuccessful) {

            if (response.code() == 200) {

                // 1.
                //val base: String? = response.body()
                //println("~~~base:$base")

                // 2.
                val str: String? = response.body()
                println("~~~str:$str")

                //회원 가입 이동
                val sign = findViewById<Button>(R.id.sign)

                sign.setOnClickListener {
                    val intent= Intent(this, SignActivity::class.java)
                    startActivity(intent)


                }
            }
        }*/
    }

    override fun onClick(view: View?) {
        val loginID = findViewById<TextView>(R.id.loginID)
        val loginPW = findViewById<TextView>(R.id.loginPw)

        when (view?.id) {

            R.id.login_Btn -> {
                val id = loginID.text.toString()
                val pw = loginPW.text.toString()
                val dto = MemberDto(id, pw, "", "", "", "", 0)
                val checkLogin = MemberDao.getInstance().login(dto)
                if (checkLogin != null) {
                    Toast.makeText(this, "환영합니다. ${checkLogin.id}님", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.signUp_Btn -> startActivity(Intent(this, SignActivity::class.java))


            R.id.google_Btn -> {

            }

            R.id.naver_Btn -> {

            }
        }
    }
}





