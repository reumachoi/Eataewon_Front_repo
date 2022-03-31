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

class SearchBbsAdapter (private val context: Context, private val dataList: ArrayList<BbsDto>) :
        RecyclerView.Adapter<SearchBbsAdapter.ItemViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.search_view_layout,
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
        private val shopPhoto = itemView.findViewById<ImageView>(R.id.shopPhotoView)
        private val shopName = itemView.findViewById<TextView>(R.id.shopNameView)
        private val address = itemView.findViewById<TextView>(R.id.addressView)
        private val hashtag = itemView.findViewById<TextView>(R.id.hashtagView)

        fun bind(bbsDto: BbsDto, context: Context) {

            shopPhoto.setImageResource(bbsDto.picture)
            shopName.text = bbsDto.shopname
            address.text = bbsDto.address
            hashtag.text = bbsDto.hashtag

            // 게시물 클릭시 BbsDetailActivity로 이동
            itemView.setOnClickListener {
                Intent(context, BbsDetailActivity::class.java).apply {

                }.run { context.startActivity(this) }
            }
        }
    }

}