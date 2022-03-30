package com.example.eataewon.connect


import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MemberService{

    @POST("/login")
    fun login(@Body dto:MemberDto): Call<MemberDto>

    @POST("/getId")
    fun getId(@Body dto:MemberDto): Call<String>

    @POST("/addmember")
    fun signup(@Body dto:MemberDto): Call<String>

    //bbs에 저장된 아이디값으로 member에서 같은아이디 유저정보 가져오기
    @POST("/bbsGetUser")
    fun bbsGetUser(@Body id:String): Call<MemberBbsDto>

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

    fun login(dto: MemberDto): MemberDto? {
        var response: Response<MemberDto>?
        println("ID:${dto.id} PWD:${dto.pwd}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(MemberService::class.java)
            val call = service?.login(dto)
            response = call?.execute()
            println("dto : ${response.toString()}")
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
            val call = service?.getId(dto)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }

        return response?.body()
    }

    fun signup(dto: MemberDto) : String?{
        var response: Response<String>?
        println(dto.toString())
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

    //bbs에 저장된 아이디값으로 member에서 같은아이디 유저정보 가져오기
    fun bbsGetUser(id:String):MemberBbsDto?{
        var response : Response<MemberBbsDto>?
        println(id)
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(MemberService::class.java)
            val call = service?.bbsGetUser(id)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }

        return response?.body()
    }


}