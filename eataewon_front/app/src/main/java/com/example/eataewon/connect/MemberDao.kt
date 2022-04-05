package com.example.eataewon.connect

import com.example.eataewon.connect.MemberBbsDto
import com.example.eataewon.connect.MemberDto
import com.example.eataewon.connect.RetrofitClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MemberService{

    @POST("/loginApp")
    fun login(@Body dto:MemberDto): Call<MemberDto>

    @POST("/getIdApp")
    fun getId(@Body id:String?=null): Call<String>

    @POST("/getEmailApp")
    fun getEmail(@Body email:String) : Call<String>

    @POST("/addmemberApp")
    fun signup(@Body dto:MemberDto): Call<String>

    //bbs에 저장된 아이디값으로 member에서 같은아이디 유저정보 가져오기
    @POST("/bbsGetUser")
    fun bbsGetUser(@Body id:String): Call<MemberBbsDto>

    @POST("/resetPwd")
    fun resetPwd(@Body dto:MemberDto):Call<Boolean>

    @POST("getProfilPicApp")
    fun getProfilPic(@Body id:String):Call<String>

    @POST("/findUserData")
    fun findUserData(@Body dto:MemberDto):Call<MemberDto>

    @POST("/updateUserData")
    fun updateUserData(@Body dto: MemberDto):Call<Boolean>
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
        println("ID:${dto.id}, PWD:${dto.pwd}")
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

    fun getId(id:String) : String?{
        var response: Response<String>?
        println("ID중복확인:${id}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(MemberService::class.java)
            val call = service?.getId(id)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        println("아이디 중복확인 결과 ${response?.body()}")
        return response?.body()
    }

    fun getEmail(email:String) : String?{
        var response: Response<String>?
        println("email: ${email}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(MemberService::class.java)
            val call = service?.getEmail(email)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        println("이메일로 계정찾기결과 찾은 아이디는: ${response?.body()}")
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
        println("회원가입 결과 ${response?.body()}")
        return response?.body()
    }

    //bbs에 저장된 아이디값으로 member에서 같은아이디 유저정보 가져오기
    fun bbsGetUser(id:String): MemberBbsDto?{
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

    fun getProfilPic(id:String):String?{
        var response : Response<String>?
        println("getProfilPic Id: ${id}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(MemberService::class.java)
            val call = service?.getProfilPic(id)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        return response?.body()
    }

    fun resetPwd(dto: MemberDto):Boolean?{
        var response : Response<Boolean>?
        println("resetPwd dto: ${dto}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(MemberService::class.java)
            val call = service?.resetPwd(dto)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        println("이메일 변경 결과?? ${response?.body()}")
        return response?.body()
    }

    fun findUserData(dto: MemberDto):MemberDto?{
        var response : Response<MemberDto>?
        println("findUserData dto: ${dto}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(MemberService::class.java)
            val call = service?.findUserData(dto)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        return response?.body()
    }

    fun updateUserData(dto: MemberDto):Boolean?{
        var response : Response<Boolean>?
        println("updateUserData dto: ${dto}")
        try {
            val retrofit = RetrofitClient.getInstance()
            val service = retrofit?.create(MemberService::class.java)
            val call = service?.updateUserData(dto)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }
        return response?.body()
    }
}