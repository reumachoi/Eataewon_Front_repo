package com.example.eataewon.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eataewon.BbsDetailActivity
import com.example.eataewon.R
import com.example.eataewon.connect.BbsDao
import com.example.eataewon.connect.BbsDto

class MypageBbsAdapter (private val context: Context, private val dataList: ArrayList<BbsDto>) :
        RecyclerView.Adapter<MypageBbsAdapter.ItemViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.mypage_bbsview_layout,
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
        //private val shopPhoto = itemView.findViewById<ImageView>(R.id.shopPhotoMyBbs)
        private val shopName = itemView.findViewById<TextView>(R.id.shopNameMyBbs)
        private val address = itemView.findViewById<TextView>(R.id.addressMyBbs)
        private val hashtag = itemView.findViewById<TextView>(R.id.hashtagMyBbs)

        fun bind(bbsDto: BbsDto, context: Context) {

            //shopPhoto.setImageResource(bbsDto.picture)
            shopName.text = bbsDto.shopname
            address.text = bbsDto.address
            hashtag.text = bbsDto.hashtag

            // 게시물 클릭시 BbsDetailActivity로 이동
            itemView.setOnClickListener {
                var result = BbsDao.getInstance().updateReadcnt(bbsDto.seq!!)    //조회수 증가
                if(result.equals("Success")) {
                    println("글번호 ${bbsDto.seq} 조회수증가~~~~~~~~~~~~~~~")
                }

                Intent(context, BbsDetailActivity::class.java).apply {
                    // 짐싸!
                    putExtra("clickBbs", bbsDto)

                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }
        }
    }

}