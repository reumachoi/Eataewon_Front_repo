package com.example.eattaewon_back_kimminki

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitBbs {

        @GET("/getBbsList")
        fun getBbsList(): Call<List<BbsDto>>

        @GET("/test")
        fun test():Call<String>
}
