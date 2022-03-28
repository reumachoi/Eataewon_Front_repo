package com.example.eattaewon

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.eattaewon.connect.MemberDto

class MypageFragment(private val homeActivity: HomeActivity): Fragment(R.layout.fragment_mypage),View.OnClickListener{
    // storage 권한

    val STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val STORAGE_CODE = 99

    lateinit var mypageProfilpicuri:TextView
    lateinit var mypageProfilpic:ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_mypage, container, false)

      // 상단바 제작 에 사용
        setHasOptionsMenu(true);

        //임시 테스트
     //   val user = MemberDto(0,"","","","","","",0,"")

        //텍스트뷰
        val mypageLikepoint = v.findViewById<TextView>(R.id.mypage_likepoint)
        val mypageName = v.findViewById<TextView>(R.id.mypage_name)
        val mypageEmail = v.findViewById<TextView>(R.id.mypage_email)
        val mypageNickname = v.findViewById<TextView>(R.id.mypage_nickname)
        val mypageProfilmsg = v.findViewById<TextView>(R.id.mypage_profilmsg)

        //이미지
        mypageProfilpic = v.findViewById(R.id.mypage_profil_image)
        mypageProfilpicuri = v.findViewById(R.id.mypage_profilpic_uri)

        //버튼
        val cancleBtn = v.findViewById<Button>(R.id.mypage_cancleBtn)
        val updateBtn = v.findViewById<Button>(R.id.mypage_updateBtn)

        val imageBtn = v.findViewById<Button>(R.id.mypageProfilpicBtn)


/*
        //텍스트뷰에 값 입력
        mypageLikepoint.text = user.likepoint.toString()
        mypageName.text = user.name
        mypageEmail.text = user.email
        mypageNickname.text = user.nickname
        mypageProfilmsg.text = user.profilMsg
        mypageProfilpic.setImageURI(user.profilPic?.toUri())
*/

        //버튼 클릭 이벤트


        cancleBtn.setOnClickListener(this)

        //사진
        imageBtn.setOnClickListener { GetAlbum() }

        //수정 버튼 이벤트
        updateBtn.setOnClickListener{

        }
        return v


    }
 // 상단바 부분
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)


        return super.onCreateOptionsMenu(menu, inflater)
    }
  // 상단바 이동
    override fun onOptionsItemSelected(item: MenuItem): Boolean {


      val intent = Intent(homeActivity,DeleteActivity::class.java)
      startActivity(intent)


        when(item?.itemId){




            //        R.id.withdraw -> logout.text = "셋팅 클릭" // 추후 추가 // 로그인 이전화면으로 뷰 전환만 해준다.
        }

        return super.onOptionsItemSelected(item)
    }




    //버튼 클릭 이벤트 내용
    override fun onClick(view: View?) {
        when(view?.id){


            R.id.mypage_cancleBtn->{

            }
        }
    }

    // uri와 이미지뷰에 저장
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                STORAGE_CODE -> {
                    val uri = data?.data
                    mypageProfilpicuri.text = uri.toString()
                    mypageProfilpic.setImageURI(uri)
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
                if(ContextCompat.checkSelfPermission(requireActivity(), permission)
                    != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(requireActivity(), permissions, type)
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
                        Toast.makeText(context, "저장소 권한을 승인해 주세요", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}

