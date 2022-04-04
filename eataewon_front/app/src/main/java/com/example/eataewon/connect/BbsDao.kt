package com.example.eataewon.connect

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface BbsService {

    @GET("/getBbsListApp")
    fun getBbsList(@Body seq:Int): Call<BbsDto>

    @POST("/bbsScrap")
    fun plusBbsScrap(@Body dto:BbsDto): Call<String>

    @POST("/likeBbs")
    fun plusBbsLike(@Body dto:BbsDto) : Call<String>

    @POST("/bbsdetail")
    fun getBbsDetail(@Body seq:Int) : Call<BbsDto>

    @POST("/bbsupdateApp")
    fun bbsUpdate(@Body dto:BbsDto) : Call<Boolean>

    @POST("/bbsdeleteApp")
    fun bbsDelete(@Body seq:Int) : Call<Boolean>

    @POST("/bbswriteApp")
    fun bbswrite(@Body dto:BbsDto) : Call<Int>

    @Headers("Content-Type: application/json")
    @POST("/plustReadcntApp")
    fun updateReadcnt(@Body seq:Int) : Call<String>

    //카카오 로컬 검색
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

    fun getBbsList(seq:Int):BbsDto?{
        var response: Response<BbsDto>?
        println("SEQ: ${seq}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.getBbsList(seq)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        return response?.body()
    }

    fun updateReadcnt(seq:Int) : String?{
        var response: Response<String>?
        println("SEQ: ${seq}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.updateReadcnt(seq)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        return response?.body()
    }

    fun bbswrite(dto:BbsDto) : Int?{
        var response: Response<Int>?
        println("DTO: ${dto}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.bbswrite(dto)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }

        return response?.body()
    }

    fun bbsUpdate(dto:BbsDto):Boolean?{
        var response: Response<Boolean>?
        println("DTO: ${dto}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.bbsUpdate(dto)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        return response?.body()
    }

    fun bbsDelete(seq:Int) : Boolean?{
        var response: Response<Boolean>?
        println("SEQ: ${seq}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.bbsDelete(seq)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        return response?.body()
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
