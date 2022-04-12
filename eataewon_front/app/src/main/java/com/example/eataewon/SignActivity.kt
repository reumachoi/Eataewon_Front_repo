package com.example.eataewon

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.example.eataewon.connect.MemberDao
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
            val id = signupID.text.toString().trim()
            val idcheck = MemberDao.getInstance().getId(id)
            if(idcheck.equals("NO")){
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

            val dto = MemberDto(id,name,pwd,email,nickname,"",0,profilmsg,0)
            val checksignup = MemberDao.getInstance().signup(dto)
            if(checksignup.equals("yes")){
                Toast.makeText(this,"${dto.id} 회원가입 완료",Toast.LENGTH_SHORT).show()
                val i = Intent(this,MainActivity::class.java)
                startActivity(i)
            }else{
                Toast.makeText(this,"회원가입 실패",Toast.LENGTH_SHORT).show()
            }
        }




    }
}




