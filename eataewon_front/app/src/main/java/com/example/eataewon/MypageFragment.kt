package com.example.eataewon


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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import com.example.eataewon.connect.MemberDto
import kotlinx.android.synthetic.main.fragment_mypage.view.*

class MypageFragment(private val homeActivity: HomeActivity): Fragment(R.layout.fragment_mypage){

    // storage 권한
    val STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val STORAGE_CODE = 99

    lateinit var mypageProfilpicuri:TextView
    lateinit var mypageProfilpic: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_mypage, container, false)

        //임시 테스트
        val user = arguments?.getParcelable<MemberDto>("user")

        //상단 툴바바
        v.toolbar.inflateMenu(R.menu.mypage_menu_item)


        setHasOptionsMenu(true)

        //텍스트뷰
        val mypageId = v.findViewById<TextView>(R.id.mypage_id)
        val mypageName = v.findViewById<TextView>(R.id.mypage_name)
        val mypageLikepoint = v.findViewById<TextView>(R.id.mypage_likepoint)
        val mypageEmail = v.findViewById<TextView>(R.id.mypage_email)
        val mypageNickname = v.findViewById<TextView>(R.id.mypage_nickname)
        val mypageProfilmsg = v.findViewById<TextView>(R.id.mypage_profilmsg)

        //이미지
        mypageProfilpic = v.findViewById(R.id.mypage_profil_image)
        mypageProfilpicuri = v.findViewById(R.id.mypage_profilpic_uri)

        //버튼
        val updateBtn = v.findViewById<Button>(R.id.mypage_updateBtn)
        val cancleBtn = v.findViewById<Button>(R.id.mypage_cancleBtn)
        val imageBtn = v.findViewById<Button>(R.id.mypageProfilpicBtn)

        //텍스트뷰에 값 입력
        mypageId.text = user?.id
        mypageName.text = user?.name
        mypageLikepoint.text = user?.likepoint.toString()
        mypageEmail.text = user?.email
        mypageNickname.text = user?.nickname
        mypageProfilmsg.text = user?.profilmsg

        //이미지 불러오기
        //mypageProfilpic.setImageURI(user?.profilPic?.toUri())

        //사진
        imageBtn.setOnClickListener { GetAlbum() }

        //툴바 메뉴 클릭
        v.toolbar.setOnMenuItemClickListener{
            when(it.itemId){
                R.id.mypage_logout->{

                    val logout = Intent(homeActivity,MainActivity::class.java)
                    startActivity(logout)
                    Toast.makeText(context,"로그아웃",Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.mypage_withdraw->{

                    val intent = Intent(homeActivity,DeleteActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(context,"회원탈퇴",Toast.LENGTH_SHORT).show()
                    true
                }
                else->false
            }
        }


        //취소 버튼 이벤트
        cancleBtn.setOnClickListener{

        }


        //수정 버튼 이벤트
        updateBtn.setOnClickListener{

        }


        return v
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

    //툴바 연결
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.mypage_menu_item,menu)
    }

   // 인기도

}







