package com.example.eattaewon

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.example.eattaewon.connect.MemberDto

// storage 권한
val STORAGE = arrayOf(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE)

val STORAGE_CODE = 99

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
        val singupProfilpic = findViewById<TextView>(R.id.signUp_profilpic_uri)

        val imageBtn = findViewById<Button>(R.id.signUp_imageBtn)
        val signupBtn = findViewById<Button>(R.id.signUP_btn)

        signupID.doAfterTextChanged {
            val id = signupID.text.toString()
            val dto = MemberDto(0,"",id,"","","","",0,"")
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
            val profilpic = singupProfilpic.text.toString()

            val dto = MemberDto(0,name,id,pwd,email,nickname,profilpic,0,profilmsg)
            val checksignup = MemberDao.getInstance().signup(dto)

            if(checksignup=="yes"){
                Toast.makeText(this,"${dto.id} 회원가입 완료",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"회원가입 실패",Toast.LENGTH_SHORT).show()
            }
        }
        imageBtn.setOnClickListener{ GetAlbum() }

    }

    // uri와 이미지뷰에 저장
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val imageView = findViewById<ImageView>(R.id.signUp_profilpic)
        val profiluri = findViewById<TextView>(R.id.signUp_profilpic_uri)

        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                STORAGE_CODE -> {
                    val uri = data?.data
                    profiluri.text = uri.toString()
                    imageView.setImageURI(uri)
                }
            }
        }
    }

    // 갤러리 취득
    fun GetAlbum(){
        if(checkPermission(STORAGE, STORAGE_CODE)){
            val itt = Intent(Intent.ACTION_PICK)
            itt.type = MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(itt, STORAGE_CODE)
        }
    }

    // 다른 권한등도 확인이 가능하도록
    fun checkPermission(permissions: Array<out String>, type:Int):Boolean{
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for (permission in permissions){
                if(ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, permissions, type)
                    return false
                }
            }
        }
        return true
    }

    //권한 승인
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_CODE -> {
                for(grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "저장소 권한을 승인해 주세요", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}




