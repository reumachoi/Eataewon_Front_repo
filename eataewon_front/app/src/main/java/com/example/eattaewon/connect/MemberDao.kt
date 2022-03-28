<<<<<<< HEAD:eataewon_Front/app/src/main/java/com/example/eataewon/connect/MemberDao.kt
<<<<<<< Updated upstream:eataewon_Front/app/src/main/java/com/example/eataewon/connect/MemberDao.kt
package com.example.eataewon.connect
=======
package com.example.eattaewon.connect
>>>>>>> parent of 2c66f7a (0328 18:17 (프로젝트,패키지명 변경)):eataewon_Front/app/src/main/java/com/example/eattaewon/connect/MemberDao.kt

=======
package com.example.eattaewon
>>>>>>> Stashed changes:eataewon_front/app/src/main/java/com/example/eattaewon/connect/MemberDao.kt

import com.example.eattaewon.connect.MemberDto
import com.example.eattaewon.connect.RetrofitClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MemberService{

    //백엔드 통신 확인용
    //@FormUrlEncoded 서버에서 인풋값 인코딩을 위함용 (post에서만 사용 + @Field)
    @GET("/test")
    fun test(
        //인풋 정의
        //@Field("userid") id:String
    ):Call<String>  //아웃풋 정의


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

}