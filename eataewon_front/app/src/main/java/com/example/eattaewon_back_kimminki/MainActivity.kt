package com.example.eattaewon_back_kimminki;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import com.example.eattaewon_back_kimminki.R
import com.example.eattaewon_back_kimminki.SignActivity
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
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
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}






