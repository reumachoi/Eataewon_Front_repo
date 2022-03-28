package com.example.eattaewon.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eattaewon.R
import com.example.eattaewon.connect.BbsDto

class HomeViewAdapter (private val context: Context, private val dataList: ArrayList<BbsDto>) :
    RecyclerView.Adapter<HomeViewAdapter.ItemViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.home_view_layout_top, parent, false)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position], context)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val curationPhoto = itemView.findViewById<ImageView>(R.id.CurationPhotoView)
        private val curationTitle = itemView.findViewById<TextView>(R.id.CurationTitleView)
        private val curaterId = itemView.findViewById<TextView>(R.id.CuratorId)

        fun bind(bbsDto: BbsDto, context: Context) {
            curationPhoto.setImageResource(bbsDto.picture)
            curationTitle.text = bbsDto.title
            curaterId.text = bbsDto.id
        }

    }
}