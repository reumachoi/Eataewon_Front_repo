package com.example.eataewon.connect



import com.example.eataewon.KakaoSearchDto
import retrofit2.Call
import retrofit2.Response

import retrofit2.http.*

interface BbsService {

        /*//post 보내고 받기
        @POST("/bbswrite")
        fun bbswrite(@Body dto:BbsDto) : Call<String>

        //get 보내고 받기
        @GET("/bbswrite")
        fun bbswrite(@Query("test") dto:BbsDto) : Call<String>*/

    @GET("/bbsdetail")
    fun getBbsDetail(@Query("seq") seq:Int) : Call<BbsDto>

     @GET("/getBbsList")
        fun getBbsList(): Call<List<BbsDto>>

    @GET("v2/local/search/keyword.json")    // Keyword.json의 정보를 받아옴
    fun getSearchKeyword(
        @Header("Authorization") key: String,     // 카카오 API 인증키 [필수]
        @Query("query") query: String             // 검색을 원하는 질의어 [필수]
        // 매개변수 추가 가능
        // @Query("category_group_code") category: String

    ): Call<KakaoSearchDto>    // 받아온 정보가 ResultSearchKeyword 클래스의 구조로 담김
}



class BbsDao {

    companion object{
        private var bbsDao: BbsDao? = null

        fun getInstance(): BbsDao {

            if(bbsDao ==null){
                bbsDao = BbsDao()
            }

            return bbsDao!!
        }
    }

    fun getBbsDetail(seq:Int) : BbsDto?{
        var response: Response<BbsDto>?
        println("SEQ: ${seq}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.getBbsDetail(seq)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }

        return response?.body()
    }
}
