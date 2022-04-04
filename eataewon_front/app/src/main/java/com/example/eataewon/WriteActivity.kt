package com.example.eataewon

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eataewon.connect.MapSearchListDto
import com.example.eataewon.databinding.ActivityWriteBinding

class WriteActivity : AppCompatActivity() {
    // storage 권한
    val STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    val STORAGE_CODE = 99
    var list = ArrayList<Uri>()
    val adapter = MultiImageAdapter(list, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        val recyclerView = findViewById<RecyclerView>(R.id.write_recyclerview)

        //텍스트
        val title = findViewById<TextView>(R.id.write_title)
        val content= findViewById<TextView>(R.id.write_content)
        val address = findViewById<EditText>(R.id.write_address)
        val hashtag = findViewById<TextView>(R.id.write_hashtag)

        //버튼
        val imagebtn = findViewById<Button>(R.id.write_imageBtn)
        val addressbtn = findViewById<Button>(R.id.write_addressBtn)
        val writebtn = findViewById<Button>(R.id.write_writeBtn)
        val canclebtn = findViewById<Button>(R.id.write_cancleBtn)

        //주소 버튼
        addressbtn.setOnClickListener {
            var i = Intent(this, SearchKakaoMapActivity::class.java)
            startActivity(i)
        }

        val searchData = intent.getParcelableExtra<MapSearchListDto>("shopData")

        println(searchData.toString())
        /*
        shopname = searchData.name
        address = searchData.road
        latitude = searchData.y
        longitude = searchData.x
        shopphnum = searchData.phone
        shopurl = searchData.place_url
        */

        //이미지 추가 버튼
        imagebtn.setOnClickListener {
            if(checkPermission(STORAGE, STORAGE_CODE)) {
                var intent = Intent(Intent.ACTION_PICK)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

                startActivityForResult(intent, STORAGE_CODE)
            }
        }

        /*// 이미지 사진 간격 맞추기
        val decoration = RecyclerViewDecoration(10)
        recyclerView.addItemDecoration(decoration)*/

        //리사이클 뷰
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false )
        recyclerView.adapter = adapter

        //글쓰기 버튼
        writebtn.setOnClickListener {

        }

        //취소 버튼
        canclebtn.setOnClickListener {
            val i = Intent(this,HomeActivity::class.java)
            startActivity(i)
        }

    }

    //여러개의 이미지 선택
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == RESULT_OK && requestCode == STORAGE_CODE) {
            list.clear()

            if (data?.clipData != null) { // 사진 여러개 선택한 경우
                val count = data.clipData!!.itemCount
                if (count > 10) {
                    Toast.makeText(this, "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG)
                    return
                }
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    list.add(imageUri)
                }

            } else { // 단일 선택
                data?.data?.let { uri ->
                    val imageUri : Uri? = data?.data
                    if (imageUri != null) {
                        list.add(imageUri)
                    }
                }
            }
            adapter.notifyDataSetChanged()
        }
    }

    //리사이클러 뷰 업데터
    class MultiImageAdapter(private val items: ArrayList<Uri>, val context: WriteActivity) :
        RecyclerView.Adapter<MultiImageAdapter.ViewHolder>() {

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
            //크기 설정
            Glide.with(context).load(item)
                .override(500, 500)
                .into(holder.image)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.write_image_item, parent, false)
            return ViewHolder(inflatedView)
        }

        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            private var view: View = v
            var image = v.findViewById<ImageView>(R.id.write_imageview)


            fun bind(listener: View.OnClickListener, item: String) {
                view.setOnClickListener(listener)
            }
        }
    }

    //사진 간격
    class RecyclerViewDecoration(private val divWidth: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.right = divWidth
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