package com.example.eataewon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.example.eataewon.connect.MemberDto


class SignActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        val signupID = findViewById<EditText>(R.id.signUp_Id)
        val signupidcheck= findViewById<TextView>(R.id.signUp_Idchek)
        val signupPw = findViewById<TextView>(R.id.signUp_Pw)
        val signupName = findViewById<TextView>(R.id.signUp_name)
        val signupEmail = findViewById<TextView>(R.id.signUp_email)
        val signupNickname = findViewById<TextView>(R.id.signUp_nickname)
        val signupProfilmsg = findViewById<TextView>(R.id.signUp_profilmsg)

        val signupBtn = findViewById<Button>(R.id.signUP_btn)

        signupID.doAfterTextChanged {
            val id = signupID.text.toString()
            val dto = MemberDto("",id,"","","",0,0,"")
            val idcheck = MemberDao.getInstance().getId(dto)
            if(idcheck=="NO"){
                signupidcheck.text = "이미 존재하는 아이디 입니다"
                signupidcheck.setTextColor(Color.RED)
            }else{
                signupidcheck.text = "사용 가능한 아이디입니다."
                signupidcheck.setTextColor(Color.BLUE)
            }
        }

        signupBtn.setOnClickListener{

            val id = signupID.text.toString()
            val pwd = signupPw.text.toString()
            val name = signupName.text.toString()
            val email = signupEmail.text.toString()
            val nickname = signupNickname.text.toString()
            val profilmsg = signupProfilmsg.text.toString()

            val dto = MemberDto(name,id,pwd,email,nickname,0,0,profilmsg)
            val checksignup = MemberDao.getInstance().signup(dto)
            if(checksignup=="yes"){
                Toast.makeText(this,"${dto.id} 회원가입 완료",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"회원가입 실패",Toast.LENGTH_SHORT).show()
            }
        }




    }
}




