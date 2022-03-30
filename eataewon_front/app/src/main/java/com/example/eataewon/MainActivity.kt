package com.example.eataewon;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.eataewon.connect.MemberDao
import com.example.eataewon.connect.MemberDto

import com.example.eataewon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),View.OnClickListener{

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    var imm: InputMethodManager? = null //EditText 키보드 내려가도록

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?;

        //디테일 테스트 버튼 (최아름)
        binding.testBtn.setOnClickListener {
            val i = Intent(this, BbsDetailActivity::class.java)
            startActivity(i)
        }


     /*   //백엔드 통신 확인용
        var result = MemberDao.getInstance().test()
        binding.loginID.setText(result.toString())
        binding.textView9.text = result.toString()
        println(result.toString()+"test 확인")*/

        val loginBtn = findViewById<Button>(R.id.login_Btn)
        val signUpBtn = findViewById<Button>(R.id.signUpAtivity_Btn)
        /*val googleBtn = findViewById<Button>(R.id.google_Btn)
        val naverBtn = findViewById<Button>(R.id.naver_Btn)*/

        loginBtn.setOnClickListener(this)
        signUpBtn.setOnClickListener(this)
       /* googleBtn.setOnClickListener(this)
        naverBtn.setOnClickListener(this)*/

    }

    override fun onClick(view: View?) {
        val loginID = findViewById<TextView>(R.id.loginID)
        val loginPW = findViewById<TextView>(R.id.loginPw)

        when (view?.id) {

            R.id.login_Btn -> {
                imm?.hideSoftInputFromWindow(loginPW.getWindowToken(), 0);

                val id = loginID.text.toString()
                val pwd = loginPW.text.toString()
                val dto = MemberDto(id, "", pwd, "", "",0, 0,"")

                //백엔드 접속후 id pw값을 찾아 login값에 dto값 넣기
                val login = MemberDao.getInstance().login(dto)

                if (login != null) {
                    Toast.makeText(this, "환영합니다. ${login.id}님", Toast.LENGTH_SHORT).show()

                    //안도현(로그인 후 홈엑티비티로 넘어가면서 intent.put으로 login값 넘기기)
                    val intent = Intent(this,HomeActivity::class.java)
                    intent.putExtra("user",login)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.signUpAtivity_Btn -> startActivity(Intent(this, SignActivity::class.java))


            /*R.id.google_Btn -> {

            }

            R.id.naver_Btn -> {

            }*/
        }
    }
}

