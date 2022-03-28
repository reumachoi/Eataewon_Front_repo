package com.example.eataewon


import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
<<<<<<< Updated upstream:eataewon_Front/app/src/main/java/com/example/eataewon/MypageFragment.kt
import com.example.eataewon.connect.MemberDto
import kotlinx.android.synthetic.main.fragment_mypage.view.*
=======
>>>>>>> Stashed changes:eataewon_front/app/src/main/java/com/example/eattaewon/MypageFragment.kt

import java.io.FileOutputStream
import java.text.SimpleDateFormat

class MypageFragment:Fragment(R.layout.fragment_mypage){ // ,View.OnClickListener  {

/*

    val STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    val CAMERA_CODE = 98
    val STORAGE_CODE = 99

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_mypage, container, false)

        val correct = view.findViewById<Button>(R.id.correct)
        val Cancel = view.findViewById<Button>(R.id.Cancel)    // 미 연결 상태 추후에 연결

        Cancel.setOnClickListener(this)
        correct.setOnClickListener(this)
        return view







        // 사진 저장
        val picture = view.findViewById<Button>(R.id.Imagebtn)
        picture.setOnClickListener {
            GetAlbum()
        }


    }

 ///  수정 이나 삭제 필요
    fun getPath(uri: Uri?): String {
        val projection = arrayOf<String>(MediaStore.Images.Media.DATA)
        val cursor: Cursor = managedQuery(uri, projection, null, null, null)
        startManagingCursor(cursor)
        val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(columnIndex)
    }




    // 요청 권한을 주는 함수 이다.  // 카메라 권한, 저장소 권한 관련 된 내용용
    override fun onRequestPermissionsResult(requestCode: Int,   // 맴버 재정의 로 호출
                                            permissions: Array<out String>, grantResults: IntArray) {   // requestCode는 int로 잡고 ,grantResults는 IntArray(배열)로 잡는다.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)  // 맴버 호출시 같이 나옴

        when(requestCode){  // 카메라 권한 허가를 요청 하는 부분 이다.

            STORAGE_CODE -> {  // 저장소 권한 허가를 요청 하는 부분 이다.
                for(grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(getContext(), "저장소 권한을 승인해 주세요", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    // 다른 권한등도 확인이 가능하도록
    fun checkPermission(permissions: Array<out String>, type:Int):Boolean{
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for (permission in permissions){
                if(ContextCompat.checkSelfPermission(requireActivity(), permission)
                    != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(requireActivity(), permissions, type)
                    return false     // 만약에 권한 승인이 처리 되지 않았을때 false로 리턴 해준다.
                }
            }
        }
        return true
    }



    // 사진 저장
    fun saveFile(fileName:String, mimeType:String, bitmap: Bitmap): Uri?{  //mimeType: 클라이언트 에게 전송된 문서 관련된 다양성을 알려주는 함수??

        var CV = ContentValues()

        // MediaStore 에 파일명, mimeType 을 지정
        CV.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
        CV.put(MediaStore.Images.Media.MIME_TYPE, mimeType)

        // 안정성 검사
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            CV.put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        // MediaStore 에 사진 저장 할수 있는 함수
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, CV)
        if(uri != null){
            var scriptor = contentResolver.openFileDescriptor(uri, "w") // contentResolver를 통해서 현제 경로는 uri 로 잡고 모드는 w 로 한다.

            val fos = FileOutputStream(scriptor?.fileDescriptor)  // 비트맵을 저장 할수 있게 해준다.

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)  // JPEG 파일 형태로 품질은 100 으로 잡았다.
            fos.close()

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){  // 뒤처리 부분 이다.
                CV.clear()
                // IS_PENDING 을 초기화
                CV.put(MediaStore.Images.Media.IS_PENDING, 0)
                contentResolver.update(uri, CV, null, null)
            }
        }
        return uri
    }

    // 결과
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {  // 맴버 재정의로 호출 해 준다.
        super.onActivityResult(requestCode, resultCode, data)

        val imageView = view!!.findViewById<ImageView>(R.id.imageView2)  //imageView  선언 및 연결

        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                CAMERA_CODE -> {
                    if(data?.extras?.get("data") != null){
                        val img = data?.extras?.get("data") as Bitmap
                        val uri = saveFile(RandomFileName(), "image/JPEG", img)
                        imageView.setImageURI(uri)
                        println("실제이미지 경로:"+ getPath(uri))
                    }
                }
                STORAGE_CODE -> {
                    val uri = data?.data
                    imageView.setImageURI(uri)
                }
            }
        }
    }

    // 파일명을 날짜로 저장 한다.
    fun RandomFileName() : String{   // 리턴값은 String
        val fileName = SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())  // currentTimeMillis : 현제 시간으로 한다.
        return fileName
    }

    // 갤러리 취득
    fun GetAlbum(){
        if(checkPermission(STORAGE, STORAGE_CODE)){   // 위에 설정한 checkPermission 가  참이였을때.
            val itt = Intent(Intent.ACTION_PICK)
            itt.type = MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(itt, STORAGE_CODE)
        }
    }


    // 액션바 생성을 위한 함수
    @Override
    private fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)


        return super.onCreateOptionsMenu(menu)
        //  return false
    }
    // 회원 탈퇴 부분
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //   val deleteText: TextView =findViewById<TextView>(R.id.deleteText)

        when(item?.itemId){


     //       R.id.withdraw -> startActivity(Intent(this,delete::class.java))

            //        R.id.withdraw -> logout.text = "셋팅 클릭" // 추후 추가 // 로그인 이전화면으로 뷰 전환만 해준다.
        }

        return super.onOptionsItemSelected(item)
    }
// 회원 가입 DB 입력

   override fun onClick(view: View?) {
        val NickCh = view!!.findViewById<EditText>(R.id.NickCh)
        val IntroCh = view!!.findViewById<EditText>(R.id.IntroCh)
//

        when (view?.id) {
            R.id.correct -> {
                val NICKNAME = NickCh.text.toString()
                val PROFLMSG = IntroCh.text.toString()


       //         var member = Member(0,NICKNAME,PROFLMSG)
       //         DBHelper.getInstance(this,"Member.db").add(member)

      //          Toast.makeText(this,"title : ${member.NICKNAME} / 추가되었습니다.", Toast.LENGTH_SHORT).show()

            }
        }


    }

*/

}





