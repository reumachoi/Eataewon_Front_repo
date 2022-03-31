package com.example.eataewon.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eataewon.R
import com.example.eataewon.connect.BbsDto


class HomeBbsAdapter (private val dataList: ArrayList<BbsDto>) :
    RecyclerView.Adapter<HomeBbsAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.homeview_bbs_layout,
            parent, false
        )
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position], position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
            }
        }
    }

