package com.example.eataewon

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eataewon.connect.MemberDao
import com.example.eataewon.connect.MemberDto

class DeleteActivity : AppCompatActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        val deletebtn = findViewById<Button>(R.id.deletebtn)



       deletebtn.setOnClickListener(this)
    }


    override fun onClick(view: View?) {
        val deletId = findViewById<EditText>(R.id.deletId)
        val deletIdPw = findViewById<EditText>(R.id.deletIdPw)
        when (view?.id) {

            R.id.deletebtn -> {
                val id = deletId.text.toString()
                val pwd = deletIdPw.text.toString()
                val dto = MemberDto(id, "",pwd , "", "","", 0,"",0)
                val deletecheck = MemberDao.getInstance().deleteMem(dto)

                if(deletecheck == "OK"){
                    Toast.makeText(this,"${dto.id} 회원 탈퇴 완료되셨습니다.",Toast.LENGTH_SHORT).show()  // 성공 메세지 출력
                }else{
                    Toast.makeText(this,"회원탈퇴를 실패 하셨습니다.",Toast.LENGTH_SHORT).show()
                }

                startActivity(Intent(this, MainActivity::class.java))   //메인 으로 이동
            }

        }
    }


}