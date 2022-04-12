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
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eataewon.connect.*
import com.example.eataewon.databinding.ActivityWriteBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class WriteActivity : AppCompatActivity() {

    val binding by lazy { ActivityWriteBinding.inflate(layoutInflater) }

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

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatted = current.format(formatter)

        //로그인 유저정보
        val prefs = getSharedPreferences("sharedPref", 0)
        val loginUserId = prefs.getString("loginUserId","로그인유저 정보없음")
        val loginUserNickname = prefs.getString("loginUserNickname","로그인유저 정보없음")
        println("${loginUserId}  ${loginUserNickname} ~~~~~~~~~~~~~")

        binding.writeUserNickname.text = loginUserId
        binding.writeDate.text = formatted

        val userProfilPic = MemberDao.getInstance().getProfilPic(loginUserId!!)
        println("글쓴이 프로필 사진 가져오기 ${userProfilPic}")
       // binding.writeProfilPic.setImageURI(Uri.parse(userProfilPic))

        val recyclerView = findViewById<RecyclerView>(R.id.write_recyclerview)

        //주소 버튼
        binding.writeAddressBtn.setOnClickListener {
            var i = Intent(this, SearchKakaoMapActivity::class.java)
            i.putExtra("editAddr",binding.writeAddress.text.toString())
            startActivity(i)
        }

        val searchData = intent.getParcelableExtra<MapSearchListDto>("shopData")

        if(searchData != null){ //지도에서 선택한 정보가 있을때 데이터 넣어주기
            binding.writeAddress.setText(searchData.road)
            binding.writeShopNameT.text = searchData.name
        }

        //이미지 추가 버튼
        binding.writeImageBtn.setOnClickListener {

            if(checkPermission(STORAGE, STORAGE_CODE)) {
                var intent = Intent(Intent.ACTION_PICK)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                startActivityForResult(intent, STORAGE_CODE)
            }
        }


        // 이미지 사진 간격 맞추기
        recyclerView.addItemDecoration(RecyclerViewDecoration(5))

        //리사이클 뷰
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false )
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        //글쓰기 버튼
        binding.writeWriteBtn.setOnClickListener {
            var title = binding.writeTitle.text.toString()
            var content = binding.writeContent.text.toString()
            var hashtag = binding.writeHashtag.text.toString()
            var shopname = searchData?.name
            var address = searchData?.road
            var shopphnum = searchData?.phone
            var shopurl = searchData?.place_url
            var latitude = searchData?.y.toString().toDouble()
            var longitude = searchData?.x.toString().toDouble()
            var id = loginUserId
            var nickname = loginUserNickname
            var wdate = current.format(formatter)

            for (i in 0 until list.size) {
                uriPath += getPath(list.get(i))+" "
            }
            println("uriPath 결과2 ${uriPath}")
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
                val seq = BbsDao.getInstance().bbswrite(dto)
                println("글쓰기 통신결과 넘어온 seq값 ${seq}!!!!!!!!!!!")

                val checkLikeP = BbsDao.getInstance().LikePWriteUp(id!!)
                if(checkLikeP==true){
                    println("글쓰기로 ${dto.id}의 호감도가 상승했습니다")
                }else{
                    println("글쓰기로 호감도 상승에 실패했습니다")
                }

                if(seq!! >0){
                    Toast.makeText(this,"글쓰기가 완료되었습니다",Toast.LENGTH_SHORT).show()
                    var i = Intent(this,BbsDetailActivity::class.java)
                    i.putExtra("writeSeq",seq)
                    startActivity(i)
                }else{
                    Toast.makeText(this,"글쓰기를 실패했습니다",Toast.LENGTH_SHORT).show()
                }
            }
        }

        //취소 버튼
        binding.writeCancleBtn.setOnClickListener {
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

    fun getPath(uri: Uri?): String {
        val projection = arrayOf<String>(MediaStore.Images.Media.DATA)
        val cursor: Cursor = managedQuery(uri, projection, null, null, null)
        startManagingCursor(cursor)
        val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(columnIndex)
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