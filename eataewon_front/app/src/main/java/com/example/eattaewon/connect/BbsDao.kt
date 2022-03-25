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

    @GET("/bbsdetail")
    fun getBbsDetail(@Query("seq") seq:Int) : Call<BbsDto>

     @GET("/getBbsList")
        fun getBbsList(): Call<List<BbsDto>>

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
