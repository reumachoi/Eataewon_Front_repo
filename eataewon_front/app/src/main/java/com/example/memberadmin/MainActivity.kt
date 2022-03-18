package com.example.memberadmin


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
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.FileOutputStream
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {




    // CAMERA,storage 권한 처리에 필요한 변수

    val STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val CAMERA_CODE = 98
    val STORAGE_CODE = 99

    override fun onCreate(savedInstanceState: Bundle?) {  //클랙 했을때
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  // 이부분을 변경(지정 Activity 와 같게) 해주어야 됩니다.

  //
// 임시 만든 프로필 부분

        var dbHelper = DBHelper(this, "mydb.db", null, 1) // DBHelper 설정 해주는 부분

        val correct = findViewById<Button>(R.id.correct)
        val NickCh = findViewById<EditText>(R.id.NickCh)
        val IntroCh =  findViewById<EditText>(R.id.IntroCh)

        var database = dbHelper.writableDatabase

        correct.setOnClickListener {
            val txt = NickCh.text
            val txt2 = IntroCh.text
            dbHelper.insert(database, txt.toString()) // dbHelper에 있는 insert 에 작용
            dbHelper.insert(database, txt2.toString()) // dbHelper에 있는 insert 에 작용
        }






        // 사진 저장
        val picture = findViewById<Button>(R.id.Imagebtn)
        picture.setOnClickListener {
            GetAlbum()
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




    // 요청 권한을 주는 함수 이다.  // 카메라 권한, 저장소 권한 관련 된 내용용
    override fun onRequestPermissionsResult(requestCode: Int,   // 맴버 재정의 로 호출
                                            permissions: Array<out String>, grantResults: IntArray) {   // requestCode는 int로 잡고 ,grantResults는 IntArray(배열)로 잡는다.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)  // 맴버 호출시 같이 나옴

        when(requestCode){  // 카메라 권한 허가를 요청 하는 부분 이다.

            STORAGE_CODE -> {  // 저장소 권한 허가를 요청 하는 부분 이다.
                for(grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "저장소 권한을 승인해 주세요", Toast.LENGTH_LONG).show()
                    }
                }
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

        val imageView = findViewById<ImageView>(R.id.imageView2)  //imageView  선언 및 연결

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


    // 위의 창을 위해 생성 한 함수
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }
}







