package com.example.eataewon

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.example.eataewon.connect.MemberDao
import com.example.eataewon.connect.MemberDto
import com.example.eataewon.databinding.ActivityFindUserIdBinding
import kotlinx.android.synthetic.main.activity_find_user_id.*

class FindUserIdActivity : AppCompatActivity() {

    val binding by lazy { ActivityFindUserIdBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.findIdBtn.setOnClickListener{
            val id = findIdText.text.toString().trim()
            println(id)
            val idcheck = MemberDao.getInstance().getId(id)

            if(idcheck.equals("NO")){
                binding.findResultText.text = "입력하신 정보와 일치하는 계정을 찾았습니다 \n 비밀번호를 재설정해주세요"
                binding.findResultText.isVisible = true
                binding.findResultText.setTextColor(Color.BLUE)
                binding.findIdResultId.text = id
            }else{
                binding.findResultText.text = "입력하신 정보와 일치하는 계정을 찾지못했습니다"
                binding.findResultText.isVisible = true
                binding.findResultText.setTextColor(Color.RED)
            }
        }

        binding.findEamilBtn.setOnClickListener{
            val email = findEmailText.text.toString().trim()

            val findId = MemberDao.getInstance().getEmail(email)

            if(findId!=""){
                binding.findResultText.text = "입력하신 정보와 일치하는 계정을 찾았습니다 \n 비밀번호를 재설정해주세요"
                binding.findResultText.isVisible = true
                binding.findResultText.setTextColor(Color.BLUE)
                binding.findIdResultId.text = findId
            }else{
                binding.findResultText.text = "입력하신 정보와 일치하는 계정을 찾지못했습니다"
                binding.findResultText.isVisible = true
                binding.findResultText.setTextColor(Color.RED)
            }
        }

        binding.resetPwdBtn.setOnClickListener {
            val pwd = resetPwdText.text.toString().trim()
            val id = binding.findIdResultId.text.toString()

            val dto = MemberDto(id,"",pwd,"","","",0,"")
            val resetPwd = MemberDao.getInstance().resetPwd(dto)

            if(resetPwd==true){
                binding.FindresultText.text = "${id} 계정의 비밀번호를 재설정했습니다"
                binding.FindresultText.isVisible = true
                binding.FindresultText.setTextColor(Color.BLUE)
            }else{
                binding.FindresultText.text = "${id} 계정의 비밀번호를 재설정을 실패했습니다"
                binding.FindresultText.isVisible = true
                binding.FindresultText.setTextColor(Color.RED)
            }
        }

        binding.finishFindBtn.setOnClickListener {
            var i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }
    }
}