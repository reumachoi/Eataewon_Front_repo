package com.example.eattaewon

import android.os.StrictMode
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitClient {
    companion object{
        private var instantce : Retrofit? = null

        fun getIntance():Retrofit?{
            if (instantce == null){
                // Network 처리에 추가한다  == HttpURLConnection
                val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)

                // Gson 은 Java 객체를 JSON 으로 변환할 수 있다
                val gson = GsonBuilder()
                    .setLenient()
                    .create()

                instantce = Retrofit.Builder()
                    .baseUrl("http://61.98.39.123:3010/")//연결
                    .addConverterFactory(GsonConverterFactory.create(gson))     // object, integer
                    .addConverterFactory(ScalarsConverterFactory.create())      // 문자열 리턴받는 경우
                    .build()
            }

            return instantce
        }
    }
}