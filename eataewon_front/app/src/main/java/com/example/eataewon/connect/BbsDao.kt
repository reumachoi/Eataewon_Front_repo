package com.example.eataewon.connect

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface BbsService {

    @GET("/getBbsListApp")
    fun getBbsListApp(@Body seq:Int): Call<BbsDto>

    @POST("/bbswriteApp")
    fun bbswrite(@Body dto:BbsDto) : Call<Int>

    @POST("/bbsupdateApp")
    fun bbsUpdate(@Body dto:BbsDto) : Call<Boolean>

    @POST("/bbsdeleteApp")
    fun bbsDelete(@Body seq:Int) : Call<Boolean>

    @Headers("Content-Type: application/json")
    @POST("/plustReadcntApp")
    fun updateReadcnt(@Body seq:Int) : Call<String>

    @POST("/bbsScrapApp")
    fun insertScrap(@Body dto:ScrapDto) : Call<Boolean>

    @POST("/bbsLikeApp")
    fun insertLike(@Body dto:LikeDto) : Call<Boolean>

    @POST("/deleteBbsScrapApp")
    fun deleteScrap(@Body dto:ScrapDto) : Call<Boolean>

    @POST("/deleteBbsLikeApp")
    fun deleteLike(@Body dto:LikeDto) : Call<Boolean>

    @POST("/bbsScrap")
    fun plusBbsScrap(@Body dto:BbsDto): Call<String>

    @POST("/likeBbs")
    fun plusBbsLike(@Body dto:BbsDto) : Call<String>

    @POST("/bbsdetail")
    fun getBbsDetail(@Body seq:Int) : Call<BbsDto>

    @POST("/bbsdelete")
    fun bbsdelete(@Body seq:Int) : Call<Boolean>

    @POST("/LikePWriteUp")
    fun LikePWriteUp(@Body id:String): Call<Boolean>

    @POST("/LikePHeartUp")
    fun LikePHeartUp(@Body id:String): Call<Boolean>

    @POST("/LikePScrapUp")
    fun LikePScrapUp(@Body id:String): Call<Boolean>

    @POST("/LikePWriteDown")
    fun LikePWriteDown(@Body id:String): Call<Boolean>

    @POST("/LikePHeartDown")
    fun LikePHeartDown(@Body id:String): Call<Boolean>

    @POST("/LikePScrapDown")
    fun LikePScrapDown(@Body id:String): Call<Boolean>

    @POST("/checkUserScrap")
    fun checkUserScrap(@Body dto:ScrapDto) : Call<Boolean>

    @POST("/checkUserLike")
    fun checkUserLike(@Body dto:LikeDto) : Call<Boolean>

    @POST("/findMyBbs")
    fun findMyBbs(@Body id:String):Call<List<BbsDto>>

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

    fun getBbsListApp(seq:Int):BbsDto?{
        var response: Response<BbsDto>?
        println("SEQ: ${seq}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.getBbsListApp(seq)
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

    fun deleteScrap(dto: ScrapDto):Boolean?{
        var response: Response<Boolean>?
        println("Dto: ${dto}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.deleteScrap(dto)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        return response?.body()
    }

    fun deleteLike(dto: LikeDto):Boolean?{
        var response: Response<Boolean>?
        println("Dto: ${dto}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.deleteLike(dto)
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

    fun insertScrap(dto:ScrapDto):Boolean?{
        var response: Response<Boolean>?
        println("Dto: ${dto}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.insertScrap(dto)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        return response?.body()
    }

    fun insertLike(dto:LikeDto):Boolean?{
        var response: Response<Boolean>?
        println("Dto: ${dto}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.insertLike(dto)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        return response?.body()
    }

    fun LikePWriteUp(id:String):Boolean?{
        var response : Response<Boolean>?
        println("글쓰기 하고난 후에 작성자 호감도 증가 LikePWriteUp Id: ${id}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.LikePWriteUp(id)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        return response?.body()
    }

    fun LikePWriteDown(id:String):Boolean?{
        var response : Response<Boolean>?
        println("LikePWriteUp Id: ${id}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.LikePWriteDown(id)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        return response?.body()
    }

    fun LikePHeartUp(id:String):Boolean?{
        var response : Response<Boolean>?
        println("LikePWriteUp Id: ${id}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.LikePHeartUp(id)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        return response?.body()
    }

    fun LikePHeartDown(id:String):Boolean?{
        var response : Response<Boolean>?
        println("LikePHeartDown Id: ${id}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.LikePHeartDown(id)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        return response?.body()
    }

    fun LikePScrapUp(id:String):Boolean?{
        var response : Response<Boolean>?
        println("LikePScrapUp Id: ${id}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.LikePScrapUp(id)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        return response?.body()
    }

    fun LikePScrapDown(id:String):Boolean?{
        var response : Response<Boolean>?
        println("LikePScrapDown Id: ${id}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.LikePScrapDown(id)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        return response?.body()
    }

    fun checkUserLike(dto:LikeDto):Boolean?{
        var response : Response<Boolean>?
        println("checkUserLike dto: ${dto}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.checkUserLike(dto)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        return response?.body()
    }

    fun checkUserScrap(dto:ScrapDto):Boolean?{
        var response : Response<Boolean>?
        println("checkUserScrap dto: ${dto}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(BbsService::class.java)
            val call = service?.checkUserScrap(dto)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        return response?.body()
    }

    fun findMyBbs(id:String):List<BbsDto>?{
        //var response: Response<List<BbsDto>>?
        println("Id: ${id}")
        //try {
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(BbsService::class.java)
        val call = service?.findMyBbs(id);
        val response = call?.execute()
        //}catch(e:Exception){
        //    response = null
        //}
        return response?.body() as List<BbsDto>
    }
}
