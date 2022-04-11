package com.example.eataewon

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.eataewon.connect.MemberDao
import com.example.eataewon.connect.MemberDto
import com.example.eataewon.databinding.ActivityFindUserIdBinding
import kotlinx.android.synthetic.main.activity_find_user_id.*

class FindUserIdActivity : AppCompatActivity() {

    val binding by lazy { ActivityFindUserIdBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.findRadioBtn.setOnCheckedChangeListener { _, checkedId ->

            when(checkedId){

                R.id.find_radioBtn1->{
                    Toast.makeText(this,"1번",Toast.LENGTH_SHORT).show()
                    find_id_layout.isVisible = true
                    find_email_layout.isInvisible = true
                }

                R.id.find_radioBtn2->{
                    Toast.makeText(this,"2번",Toast.LENGTH_SHORT).show()
                    find_id_layout.isInvisible = true
                    find_email_layout.isVisible = true
                }

            }
        }
        binding.findIdBtn.setOnClickListener{
            val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            val id = binding.findIdText.text.toString().trim()
            println(id)
            val idcheck = MemberDao.getInstance().getId(id)


            //안도현 변경 레이아웃으로 합병
            if(idcheck.equals("NO")){
                binding.findResultText.text = "입력하신 정보와 일치하는 계정을 찾았습니다 \n 비밀번호를 재설정해주세요"
                binding.findResultText.isVisible = true
                binding.findResultText.setTextColor(Color.BLUE)

                //추가
                find_pw_layout.isVisible = true

                binding.findIdResultId.text = id

            }else{
                binding.findResultText.text = "입력하신 정보와 일치하는 계정을 찾지못했습니다"
                binding.findResultText.isVisible = true
                binding.findResultText.setTextColor(Color.RED)
            }
        }

        //안도현 변경 레이아웃으로 합병
        binding.findEamilBtn.setOnClickListener{
            val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            val email = findEmailText.text.toString().trim()
            val findId = MemberDao.getInstance().getEmail(email)

            if(findId!=""){
                binding.findResultText.text = "입력하신 정보와 일치하는 계정을 찾았습니다 \n 비밀번호를 재설정해주세요"
                binding.findResultText.isVisible = true
                binding.findResultText.setTextColor(Color.BLUE)

                //추가
                find_pw_layout.isVisible = true

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

            val dto = MemberDto(id,"",pwd,"","","",0,"",0)
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