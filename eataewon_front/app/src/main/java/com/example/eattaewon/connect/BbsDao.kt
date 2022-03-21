package com.example.eattaewon.connect

import retrofit2.Call
import retrofit2.http.GET

interface BbsDao {

        @GET("/getBbsList")
        fun getBbsList(): Call<List<BbsDto>>


}
