package com.example.eattaewon_back_kimminki

import com.example.eattaewon_back_kimminki.connect.MemberDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MemberService{

    /// 3. object 를 보내고 받기
    @POST("/login")
    fun login(@Body dto: MemberDto): Call<MemberDto>


}

class MemberDao {

    companion object{
        var memberDao:MemberDao? = null

        fun getInstance():MemberDao{

            if(memberDao==null){
                memberDao = MemberDao()
            }

            return memberDao!!
        }
    }

    fun login(dto: MemberDto): MemberDto? {
        var response: Response<MemberDto>?
        println("ID:${dto}")
        try {
            val retrofit = RetrofitClient.getIntance()
            val service = retrofit?.create(MemberService::class.java)
            val call = service?.login(dto)
            response = call?.execute()
        }catch(e:Exception){
            response = null
        }

        return response?.body()

    }

}