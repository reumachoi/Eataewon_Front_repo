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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eataewon.Adapter.MypageBbsAdapter
import com.example.eataewon.Adapter.SearchBbsAdapter
import com.example.eataewon.connect.BbsDao
import com.example.eataewon.connect.BbsDto
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

        //user데이터 넘겨주기
        var mypageGetBbsFragment = mypageGetBbsFragment()
        var bundle = Bundle()
        bundle.putString("mypageuser",user?.id)
        mypageGetBbsFragment.arguments = bundle

        //본인이 쓴 글 먼저 보여주기
        childFragmentManager.beginTransaction()
            .replace(R.id.inMypageFragment, mypageGetBbsFragment)
            .commit()



        //상단 툴바바
        v.toolbar.inflateMenu(R.menu.mypage_menu_item)

        setHasOptionsMenu(true)

        //텍스트뷰
        val mypageId = v.findViewById<TextView>(R.id.mypage_id)
        val mypageName = v.findViewById<TextView>(R.id.mypage_name)
        val mypageLikepoint = v.findViewById<TextView>(R.id.mypage_likepoint)

        //이미지
        mypageProfilpic = v.findViewById(R.id.mypage_profil_image)
        val imageBtn = v.findViewById<Button>(R.id.mypageProfilpicBtn)

        //텍스트뷰에 값 입력
        mypageId.text = user?.id
        mypageName.text = user?.name
        mypageLikepoint.text = user?.likepoint.toString()

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

                R.id.mypage_userdelet->{

                    val intent = Intent(homeActivity,DeleteActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(context,"회원탈퇴",Toast.LENGTH_SHORT).show()
                    true
                }
                else->false
            }
        }



        /*if(v.lookMeBtn.isPressed == true){
            Toast.makeText(homeActivity,"lookMeBtn click",Toast.LENGTH_SHORT).show()
            v.lookMyBbsBtn.isPressed = false
        }else if(v.lookMyBbsBtn.isPressed == true){
            v.lookMeBtn.isPressed = false
        }*/

        v.lookMyBbsBtn.setOnClickListener {
            //v.lookMeBtn.setBackgroundColor(Color.rgb(255, 255, 255))

            val mypageGetBbsFragment = mypageGetBbsFragment()
            childFragmentManager.beginTransaction()
                .replace(R.id.inMypageFragment, mypageGetBbsFragment)
                .commit()
        }

        v.lookMeBtn.setOnClickListener {
            //v.lookMyBbsBtn.setBackgroundColor(Color.rgb(255, 255, 255))

            val mypageUpdateFragment = mypageUpdateFragment()
            childFragmentManager.beginTransaction()
                .replace(R.id.inMypageFragment, mypageUpdateFragment)
                .commit()
        }

        return v
    }

    companion object {
        // 테스트 데이터
        val testList = arrayListOf<BbsDto>(
            BbsDto(
                "aaa","닉네임1", 1, "가장 사랑받는 공간인데 두 줄 테스트를 위한 장문 작성을 시도 추가로 작성해야 두 줄로 보임", "플랫화이트가 맛있는 카페",
                R.drawable.cafe1, "#카페", "2022-03-22",
                "폰트커피", "서울 영등포구 경인로77가길 6 1층", "02-123-1234","https://naver.com",37.512218, 126.8925455,
                0, 0,""
            ),
            BbsDto(
                "bbb","닉네임2", 2, "두 번째로 사랑받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.cafe2, "#카페", "2022-03-22",
                "프릳츠커피", "서울 영등포구 경인로77가길 6 1층","02-123-1234","https://naver.com", 37.512218, 126.8925455,
                0, 0,""
            ),
            BbsDto(
                "ccc","닉네임3", 3, "세 번째로 사랑받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.cafe3, "#카페", "2022-03-22",
                "스타벅스", "서울 영등포구 경인로77가길 6 1층","02-123-1234","https://naver.com", 37.512218, 126.8925455,
                0, 0,""
            ),
            BbsDto(
                "ddd", "닉네임4",4, "네 번째로 사랑받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.cafe4, "#카페", "2022-03-22",
                "투썸플레이스", "서울 영등포구 경인로77가길 6 1층","02-123-1234","https://naver.com", 37.512218, 126.8925455,
                0, 0,""
            ),
            BbsDto(
                "eee","닉네임5", 5, "다섯 번째로 사랑받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.cafe5, "#카페", "2022-03-22",
                "이디야커피", "서울 영등포구 경인로77가길 6 1층","02-123-1234","https://naver.com", 37.512218, 126.8925455,
                0, 0,""
            ),
            BbsDto(
                "fff","닉네임6", 5, "가장 미움받는 공간", "플랫화이트가 맛있는 카페",
                R.drawable.food1, "#카페", "2022-03-22",
                "이디야커피", "서울 영등포구 경인로77가길 6 1층","02-123-1234","https://naver.com", 37.512218, 126.8925455,
                0, 0,""
            )
        )
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
}







