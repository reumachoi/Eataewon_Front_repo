package com.example.eataewon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.eataewon.connect.MemberDao
import com.example.eataewon.connect.MemberDto

class mypageUpdateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_mypage_update, container, false)

        //로그인 유저정보
        val prefs = requireActivity().getSharedPreferences("sharedPref", 0)
        val id = prefs.getString("loginUserId","로그인유저 정보없음")
        var nickname = prefs.getString("loginUserNickname","로그인유저 정보없음")
        println("${id}  ${nickname} ~~~~~~~~~~~~~")

        val dto = MemberDto(id,"","","",nickname,"",0,"",0)
        println("dto result ~~~~ ${dto}")
        val user = MemberDao.getInstance().findUserData(dto)
        println("user data ~~~~~~~~ ${user}")

        val mpEamil = view?.findViewById<EditText>(R.id.mypage_email)
        val mpNickname = view?.findViewById<EditText>(R.id.mypage_nickname)
        val mpMsg = view?.findViewById<EditText>(R.id.mypage_profilmsg)

        mpEamil?.setText(user?.email.toString())
        mpNickname?.setText(user?.nickname.toString())
        mpMsg?.setText(user?.profilmsg.toString())

        val updateBtn = view?.findViewById<Button>(R.id.mypageUserupdateBtn)

        updateBtn?.setOnClickListener {
            val email = mpEamil?.text.toString()
            nickname = mpNickname?.text.toString()
            val profilmsg = mpMsg?.text.toString()

            val dto = MemberDto(id,"","",email,nickname,"",0,profilmsg,0)
            val updateUserData = MemberDao.getInstance().updateUserData(dto)
            println("updateUserData result : ${updateUserData}")

            if(updateUserData==true){
                println("아이디: ${id} 인 유저의 정보가 변경되었습니다")
            }else{
                println("아이디: ${id} 인 유저의 정보 변경을 실패했습니다")
            }
        }

        return view
    }


}