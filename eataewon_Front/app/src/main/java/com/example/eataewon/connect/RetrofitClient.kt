package com.example.eataewon.connect

import android.os.StrictMode
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitClient {
    companion object{
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
                    .baseUrl("http://192.168.35.3:3000")
                    //김나현 ip(192.168.35.3:3000)
                    //최아름 ip(172.30.1.17:3000), 연결 안도현(61.98.39.123:3010)  윤동호(192.168.1.139:3000)
                    //.addConverterFactory(GsonConverterFactory.create(gson))     // object, integer
                    .addConverterFactory(ScalarsConverterFactory.create())      // 문자열 리턴받는 경우
                    .build()
            }

            return instance
        }
    }
}