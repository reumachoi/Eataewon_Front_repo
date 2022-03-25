package com.example.eattaewon

import com.example.eattaewon.connect.MemberDto
import com.example.eattaewon.connect.RetrofitClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MemberService{

    //백엔드 통신 확인용
    @GET("/test")
    fun test():Call<String>

    /// 3. object 를 보내고 받기
    @POST("/login")
    fun login(@Body dto:MemberDto): Call<MemberDto>

    @POST("/getId")
    fun getId(@Body dto:MemberDto): Call<String>

    @POST("/addmember")
    fun signup(@Body dto:MemberDto): Call<String>
}

class MemberDao {

    companion object{
        private var memberDao: MemberDao? = null

        fun getInstance(): MemberDao {

            if(memberDao ==null){
                memberDao = MemberDao()
            }

            return memberDao!!
        }
    }

    //백엔드 통신 확인용
    fun test(): String? {
        val retrofit = RetrofitClient.getInstance()
        val service = retrofit?.create(MemberService::class.java)
        val call = service?.test()
        var response = call?.execute()
        return response?.body()
    }

    fun login(dto: MemberDto): MemberDto? {
        var response: Response<MemberDto>?
        println("ID:${dto.id}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(MemberService::class.java)
            val call = service?.login(dto)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }

        return response?.body()

    }

    fun getId(dto: MemberDto) : String?{
        var response: Response<String>?
        println("ID:${dto.id}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(MemberService::class.java)
            val call = service?.signup(dto)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }

        return response?.body()
    }

    fun signup(dto: MemberDto) : String?{
        var response: Response<String>?
        println("name:${dto.name}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(MemberService::class.java)
            val call = service?.signup(dto)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }

        return response?.body()
    }

}