package com.example.eattaewon.connect

import com.example.eattaewon.MemberDao
import com.example.eattaewon.MemberService
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BbsService {

        /*//post 보내고 받기
        @POST("/bbswrite")
        fun bbswrite(@Body dto:BbsDto) : Call<String>

        //get 보내고 받기
        @GET("/bbswrite")
        fun bbswrite(@Query("test") dto:BbsDto) : Call<String>*/



     @GET("/getBbsList")
        fun getBbsList(): Call<List<BbsDto>>

        @POST("/bbsScrap")
        fun plusBbsScrap(@Body dto:BbsDto): Call<String>

        @POST("/likeBbs")
        fun plusBbsLike(@Body dto:BbsDto) : Call<String>

        @POST("/bbsdetail")
        fun getBbsDetail(@Body seq:Int) : Call<BbsDto>


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
