package com.example.eataewon.connect


import com.example.eataewon.KakaoSearchDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface BbsService {

        @GET("/getBbsList")
        fun getBbsList(): Call<List<BbsDto>>

        @POST("/bbsScrap")
        fun plusBbsScrap(@Body dto:BbsDto): Call<String>

        @POST("/likeBbs")
        fun plusBbsLike(@Body dto:BbsDto) : Call<String>

        @POST("/bbsdetail")
        fun getBbsDetail(@Body seq:Int) : Call<BbsDto>

        @GET("v2/local/search/keyword.json")    // Keyword.json의 정보를 받아옴
        fun getSearchKeyword(
            @Header("Authorization") key: String,     // 카카오 API 인증키 [필수]
            @Query("query") query: String             // 검색을 원하는 질의어 [필수]
            // 매개변수 추가 가능
            // @Query("category_group_code") category: String

        ): Call<KakaoSearchDto>
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


    fun plusBbsLike(dto:BbsDto) : String?{
        var response: Response<String>?
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.plusBbsLike(dto)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }

        return response?.body()
    }

    fun plusBbsScrap(dto:BbsDto) : String?{
        var response: Response<String>?
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.plusBbsScrap(dto)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }

        return response?.body()
    }


}
