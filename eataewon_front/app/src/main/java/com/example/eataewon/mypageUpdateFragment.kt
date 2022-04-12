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
        // Inflate the layout for this fragment

            //로그인 유저정보
            val prefs = requireActivity().getSharedPreferences("sharedPref", 0)
            val id = prefs.getString("loginUserId","로그인유저 정보없음")
            var nickname = prefs.getString("loginUserNickname","로그인유저 정보없음")
            println("${id}  ${nickname} ~~~~~~~~~~~~~")
            val dto = MemberDto(id,"","","",nickname,"",0,"",0)
            val user = MemberDao.getInstance().findUserData(dto)

            val mpEamil = view?.findViewById<EditText>(R.id.mypage_email)
            val mpNickname = view?.findViewById<EditText>(R.id.mypage_nickname)
            val mpMsg = view?.findViewById<EditText>(R.id.mypage_profilmsg)

            mpEamil?.setText(user?.email.toString())
            mpNickname?.setText(user?.nickname.toString())
            mpMsg?.setText(user?.profilmsg.toString())

                val updateBtn = view?.findViewById<Button>(R.id.mypage_updateBtn)

                val email = mpEamil.toString()
                nickname = mpNickname.toString()
                val profilmsg = mpMsg.toString()

                updateBtn?.setOnClickListener {
                        val dto = MemberDto(id,"","",email,nickname,"",0,profilmsg,0)
                        val updateUserData = MemberDao.getInstance().updateUserData(dto)
                    if(updateUserData==true){
                        println("아이디: ${id} 인 유저의 정보가 변경되었습니다")
                    }else{
                        println("아이디: ${id} 인 유저의 정보 변경을 실패했습니다")
                    }
                }

        return inflater.inflate(R.layout.fragment_mypage_update, container, false)
    }


}