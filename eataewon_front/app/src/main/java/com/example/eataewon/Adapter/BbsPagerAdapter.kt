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
import com.example.eataewon.connect.BbsDto

class BbsPagerAdapter (private val context: Context, private val datatList: ArrayList<BbsDto>) :
    RecyclerView.Adapter<BbsPagerAdapter.PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.homeview_bbs_layout,
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
        private val shopName = itemView.findViewById<TextView>(R.id.ShopName)
        private val shopLocation = itemView.findViewById<TextView>(R.id.ShopLocation)
        private val bbsPhotoView = itemView.findViewById<ImageView>(R.id.BbsPhotoView)
        private val userProfilePic = itemView.findViewById<ImageView>(R.id.UserProfilePic)
        private val userId = itemView.findViewById<TextView>(R.id.UserId)
        private val bbsContent = itemView.findViewById<TextView>(R.id.BbsContent)

        fun bind(bbsDto: BbsDto, position: Int) {
            shopName.text = bbsDto.shopname
            shopLocation.text = bbsDto.address
            bbsPhotoView.setImageResource(bbsDto.picture)
            userProfilePic.setImageResource(bbsDto.picture)
            userId.text = bbsDto.id
            bbsContent.text = bbsDto.content

            // 게시물 클릭시 BbsDetailActivity로 이동
            itemView.setOnClickListener {
                Intent(context, BbsDetailActivity::class.java).apply {

                }.run { context.startActivity(this) }
            }
        }
    }
}