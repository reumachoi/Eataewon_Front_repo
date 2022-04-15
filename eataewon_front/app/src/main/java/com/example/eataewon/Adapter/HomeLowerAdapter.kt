package com.example.eataewon.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.eataewon.BbsDetailActivity
import com.example.eataewon.R
import com.example.eataewon.connect.BbsDao
import com.example.eataewon.connect.BbsDto
import com.example.eataewon.connect.MemberBbsDto


class HomeLowerAdapter (private val context: Context, private val dataList: ArrayList<MemberBbsDto>) :
    RecyclerView.Adapter<HomeLowerAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.home_lower_layout,
            parent, false
        )
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position], context)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val shopName = itemView.findViewById<TextView>(R.id.ShopName)
        private val shopLocation = itemView.findViewById<TextView>(R.id.ShopLocation)
        private val bbsPhotoView1 = itemView.findViewById<ImageView>(R.id.BbsPhotoView1)
        /*private val bbsPhotoView2 = itemView.findViewById<ImageView>(R.id.BbsPhotoView2)
        private val bbsPhotoView3 = itemView.findViewById<ImageView>(R.id.BbsPhotoView3)
        private val bbsPhotoView4 = itemView.findViewById<ImageView>(R.id.BbsPhotoView4)*/
        private val userProfilePic = itemView.findViewById<ImageView>(R.id.UserProfilePic)
        private val userNickname = itemView.findViewById<TextView>(R.id.UserNickName)
        private val bbsContent = itemView.findViewById<TextView>(R.id.BbsContent)

        fun bind(memberBbsDto: MemberBbsDto, context: Context) {

            // 매장 사진을 공백 간격일 때마다 잘라서 배열에 저장
            val picArray = memberBbsDto.picture!!.split(" ")

            shopName.text = memberBbsDto.shopname
            shopLocation.text = memberBbsDto.address

            bbsPhotoView1.setImageURI(Uri.parse(picArray[0]))
            /*bbsPhotoView2.setImageURI(Uri.parse(picArray[1]))
            bbsPhotoView3.setImageURI(Uri.parse(picArray[2]))
            bbsPhotoView4.setImageURI(Uri.parse(picArray[3]))*/
            userProfilePic.setImageURI(Uri.parse(memberBbsDto.profilpic))
            userNickname.text = memberBbsDto.nickname
            bbsContent.text = memberBbsDto.content

            val bbsdto = BbsDto(memberBbsDto.id, memberBbsDto.nickname, memberBbsDto.seq, memberBbsDto.title,memberBbsDto.content,
                memberBbsDto.picture, memberBbsDto.hashtag, memberBbsDto.wdate, memberBbsDto.shopname, memberBbsDto.address,
                memberBbsDto.shopphnum, memberBbsDto.shopurl, memberBbsDto.latitude,memberBbsDto.longitude, memberBbsDto.readcnt, memberBbsDto.likecnt)


                bbsPhotoView1.setOnClickListener {
                    println("bbsPhotoView1 click~!~!~!~!~!~!~!~!~!~!~!~!~!~!")
                    itemClick(memberBbsDto.seq, bbsdto)
                }

                // 게시물 클릭시 BbsDetailActivity로 이동
                itemView.setOnClickListener {
                    println("itemview click~!~!~!~!~!~!~!~!~!~!~!~!~!~!")
                   itemClick(memberBbsDto.seq, bbsdto)
                }

            }
        }

        fun itemClick(seq:Int, bbsdto:BbsDto){
            println("아이템뷰 클릭클릭")
            var result = BbsDao.getInstance().updateReadcnt(seq)    //조회수 증가
            if(result.equals("Success")) {
                println("글번호 ${seq} 조회수증가~~~~~~~~~~~~~~~")
            }

            Intent(context, BbsDetailActivity::class.java).apply {
                // 짐싸!

                putExtra("clickBbs", bbsdto)

                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }.run { context.startActivity(this) }
        }
    }

