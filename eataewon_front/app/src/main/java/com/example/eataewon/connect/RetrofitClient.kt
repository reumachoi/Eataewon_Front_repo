package com.example.eataewon.connect

import android.os.StrictMode
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitClient {
    companion object{

        private const val BASE_URL = "https://openapi.naver.com/v1/"
        private var instance: Retrofit? = null

        fun getInstance(): Retrofit?{
            if(instance == null) {
                val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)

                // Gson 은 Java 객체를 JSON 으로 변환할 수 있다
                val gson = GsonBuilder()
                    .setLenient()
                    .create()

                instance = Retrofit.Builder()
                    .baseUrl("http://172.30.1.17:3000")//최아름 ip("http://172.30.1.17:3000"), 연결 안도현(61.98.39.123:3010)  윤동호(192.168.1.139:3000)
                    .addConverterFactory(ScalarsConverterFactory.create())      // 문자열 리턴받는 경우
                    .addConverterFactory(GsonConverterFactory.create(gson))     // object, integer
                    .build()
            }

            return instance
        }
    }
}