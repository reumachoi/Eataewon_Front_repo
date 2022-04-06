package com.example.eataewon

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.example.eataewon.connect.MemberDao
import com.example.eataewon.connect.MemberDto
import com.example.eataewon.databinding.ActivitySignBinding


class SignActivity : AppCompatActivity() {

    val binding by lazy {ActivitySignBinding.inflate(layoutInflater)}

    // storage 권한
    val STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    var uriPath = ""    //사진절대경로
    var profilImg: String? = null
    val STORAGE_CODE = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val signupID = findViewById<EditText>(R.id.signUp_Id)
        val signupidcheck= findViewById<TextView>(R.id.signUp_Idchek)
        val signupPw = findViewById<TextView>(R.id.signUp_Pw)
        val signupName = findViewById<TextView>(R.id.signUp_name)
        val signupEmail = findViewById<TextView>(R.id.signUp_email)
        val signupNickname = findViewById<TextView>(R.id.signUp_nickname)
        val signupProfilmsg = findViewById<TextView>(R.id.signUp_profilmsg)

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

        binding.signUpImageBtn.setOnClickListener {
            if(checkPermission(STORAGE, STORAGE_CODE)) {
                var intent = Intent(Intent.ACTION_PICK)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                GetAlbum()
            }
        }

        binding.signUPBtn.setOnClickListener{

            val id = signupID.text.toString()
            val pwd = signupPw.text.toString()
            val name = signupName.text.toString()
            val email = signupEmail.text.toString()
            val nickname = signupNickname.text.toString()
            val profilmsg = signupProfilmsg.text.toString()
            val profilpic = profilImg


            val dto = MemberDto(id,name,pwd,email,nickname,profilpic,0,profilmsg)
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


    fun getPath(uri: Uri?): String {
        val projection = arrayOf<String>(MediaStore.Images.Media.DATA)
        val cursor: Cursor = managedQuery(uri, projection, null, null, null)
        startManagingCursor(cursor)
        val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(columnIndex)
    }

    fun GetAlbum(){
        if(checkPermission(STORAGE, STORAGE_CODE)){
            val itt = Intent(Intent.ACTION_PICK)
            itt.type = MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(itt, STORAGE_CODE)
        }
    }

    // 갤러리 화면에서 이미지를 선택한 경우 현재 화면에 보여준다.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            STORAGE_CODE -> {
                data?:return
                val uri = data.data as Uri
                // 이미지 URI를 가지고 하고 싶은거 하면 된다.
                profilImg = getPath(uri)
                println("사진절대경로 ${profilImg}~~~")
                binding.signUpProfilpic.setImageURI(Uri.parse(profilImg))
            }

            else -> {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
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
}




