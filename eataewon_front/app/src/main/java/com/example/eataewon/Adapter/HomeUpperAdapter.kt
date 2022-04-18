package com.example.eataewon.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.eataewon.BbsDetailActivity
import com.example.eataewon.R
import com.example.eataewon.connect.*

class HomeUpperAdapter(private val context: Context, private val datatList: ArrayList<BbsDto>) :
    RecyclerView.Adapter<HomeUpperAdapter.PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.home_upper_layout,
            parent, false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(datatList[position], position)
    }

    override fun getItemCount(): Int {
        return datatList.size
    }

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val curationPhoto = itemView.findViewById<ImageView>(R.id.CurationPhotoView)
        private val curationTitle = itemView.findViewById<TextView>(R.id.CurationTitleView)
        private val curaterNickame = itemView.findViewById<TextView>(R.id.CuratorNickName)

        fun bind(bbsDto: BbsDto, position: Int) {

            // 매장 사진을 공백 간격일 때마다 잘라서 배열에 저장
            val picArray = bbsDto.picture!!.split(" ")

            curationPhoto.setImageURI(Uri.parse(picArray[0]))
            curationTitle.text = bbsDto.title
            curaterNickame.text = (bbsDto.nickname + " 큐레이션")


            // 게시물 클릭시 BbsDetailActivity로 이동
            itemView.setOnClickListener {
                var result = BbsDao.getInstance().updateReadcnt(bbsDto.seq!!)    //조회수 증가
                if(result.equals("Success")) {
                    println("글번호 ${bbsDto.seq} 조회수증가~~~~~~~~~~~~~~~")
                }

                Intent(context, BbsDetailActivity::class.java).apply {

                    println("${bbsDto.seq}")
                    // 짐싸!
                    putExtra("clickBbs", bbsDto)

                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }
        }
    }
}