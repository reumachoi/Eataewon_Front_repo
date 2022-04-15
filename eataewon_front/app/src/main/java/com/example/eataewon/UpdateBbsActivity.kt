package com.example.eataewon

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eataewon.connect.BbsDao
import com.example.eataewon.connect.BbsDto
import com.example.eataewon.connect.MapSearchListDto
import com.example.eataewon.databinding.ActivityUpdateBbsBinding
import kotlinx.android.synthetic.main.fragment_search.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UpdateBbsActivity : AppCompatActivity() {

    val binding by lazy { ActivityUpdateBbsBinding.inflate(layoutInflater) }

    // storage 권한
    val STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    var uriPath = ""    //사진절대경로
    val STORAGE_CODE = 99
    var list = ArrayList<Uri>()
    val adapter = MultiImageAdapter(list, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<BbsDto>("passUpdate")
        println("글상세페이지에서 수정페이지로 넘어온 데이터: ${data.toString()}")

        binding.updateTitle.setText(data?.title)
        binding.updateDate.text = data?.wdate
        binding.updateUserNickname.text = data?.nickname
        binding.updateAddress.setText(data?.address)
        binding.updateShopNameT.text = data?.shopname
        binding.updateContent.setText(data?.content)
        binding.updateHashtag.setText(data?.hashtag)

        //주소 버튼
        binding.updateAddressBtn.setOnClickListener {
            var i = Intent(this, SearchKakaoMapActivity::class.java)
            i.putExtra("editAddr",binding.updateAddress.text.toString())
            startActivity(i)
        }

        val searchData = intent.getParcelableExtra<MapSearchListDto>("shopData")

        if(searchData != null){ //지도에서 선택한 정보가 있을때 데이터 넣어주기
            binding.updateAddress.setText(searchData.road)
            binding.updateShopNameT.text = searchData.name
        }

        binding.updateImgBtn.setOnClickListener {
            if (checkPermission(STORAGE, STORAGE_CODE)) {
                var intent = Intent(Intent.ACTION_PICK)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                startActivityForResult(intent, STORAGE_CODE)
            }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.updateRecyclerview)

        // 이미지 사진 간격 맞추기
        recyclerView.addItemDecoration(RecyclerViewDecoration(5))

        //리사이클 뷰
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)


        //글쓰기 버튼
        binding.updateBtn.setOnClickListener {
            var title = binding.updateTitle.text.toString()
            var content = binding.updateContent.text.toString()
            var hashtag = binding.updateHashtag.text.toString()
            var shopname = searchData?.name
            var address = searchData?.road
            var shopphnum = searchData?.phone
            var shopurl = searchData?.place_url
            var latitude = searchData?.y.toString().toDouble()
            var longitude = searchData?.x.toString().toDouble()
            var id = data?.id
            var nickname = data?.nickname
            var wdate = data?.wdate

            for (i in 0 until list.size) {
                uriPath += getPath(list.get(i))+" "
            }

            println("uriPath 결과 ${uriPath}")
            var picture = uriPath

            if(title==""){
                Toast.makeText(this,"제목이 작성되지 않았습니다 다시 작성해주세요",Toast.LENGTH_SHORT).show()
            }else if(content==""){
                Toast.makeText(this,"내용이 작성되지 않았습니다 다시 작성해주세요",Toast.LENGTH_SHORT).show()
            }else if(address==""){
                Toast.makeText(this,"주소가 작성되지 않았습니다 다시 작성해주세요",Toast.LENGTH_SHORT).show()
            }else if(uriPath==""){
                Toast.makeText(this,"사진이 추가되지 않았습니다 다시 추가해주세요",Toast.LENGTH_SHORT).show()
            }else{


                val dto = BbsDto(id,nickname,null,title,content,picture,hashtag,wdate,
                    shopname,address,shopphnum,shopurl,latitude, longitude,0,0)

                println("writeactivity dto확인 ${dto}")
                val result = BbsDao.getInstance().bbsUpdate(dto)
                println("글쓰기 수정결과 ${result}!!!!!!!!!!!")

                if(result==true){
                    Toast.makeText(this,"글수정이 완료되었습니다",Toast.LENGTH_SHORT).show()
                    var i = Intent(this,HomeActivity::class.java)
                    i.putExtra("writeSeq",data?.seq)
                    startActivity(i)
                }else{
                    Toast.makeText(this,"글수정을 실패했습니다",Toast.LENGTH_SHORT).show()
                }
            }
        }

        //취소 버튼
        binding.updateCancleBtn.setOnClickListener {
            val i = Intent(this,HomeActivity::class.java)
            startActivity(i)
        }

    }

    //여러개의 이미지 선택
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == STORAGE_CODE) {

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

    fun getPath(uri: Uri?): String {
        val projection = arrayOf<String>(MediaStore.Images.Media.DATA)
        val cursor: Cursor = managedQuery(uri, projection, null, null, null)
        startManagingCursor(cursor)
        val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(columnIndex)
    }

    //리사이클러 뷰 업데터
    class MultiImageAdapter(private val items: ArrayList<Uri>, val context: UpdateBbsActivity) :
        RecyclerView.Adapter<MultiImageAdapter.ViewHolder>() {

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]

            //크기 설정
            Glide.with(context).load(item)
                .override(500, 500)
                .into(holder.image)

            holder.bind(items[position],context, items, this)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.write_image_item, parent, false)
            return ViewHolder(inflatedView)
        }

        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

            var image = v.findViewById<ImageView>(R.id.write_imageview)
            var xButton = v.findViewById<ImageButton>(R.id.imgDeleteBtn)
            var pos: Int? = null

            fun bind(item: Uri, context: Context, items: ArrayList<Uri>, adapter: MultiImageAdapter) {
                xButton.setOnClickListener {
                    println(items.toString())
                    if (items.contains(item)) {
                        items.remove(item)
                        pos = items.indexOf(item)
                        println(items.toString())
                        adapter.notifyDataSetChanged()
                    }
                }

            }
        }
    }

    //사진 간격
    class RecyclerViewDecoration(private val divWidth: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)

            val position = parent.getChildAdapterPosition(view)
            val count = state.itemCount
            val offset = divWidth

            if(position==0){
                outRect.left = offset
            }else if(position==count-1){
                outRect.right = offset
            }else{
                outRect.left = offset
                outRect.right = offset
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