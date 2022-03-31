package com.example.eataewon.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eataewon.R
import com.example.eataewon.connect.BbsDto

class HomePagerAdapter(private val datatList: ArrayList<BbsDto>) : RecyclerView.Adapter<HomePagerAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val curationPhoto = itemView.findViewById<ImageView>(R.id.CurationPhotoView)
        private val curationTitle = itemView.findViewById<TextView>(R.id.CurationTitleView)
        private val curaterId = itemView.findViewById<TextView>(R.id.CuratorId)

        fun bind(bbsDto: BbsDto, position: Int) {
            curationPhoto.setImageResource(bbsDto.picture)
            curationTitle.text = bbsDto.title
            curaterId.text = (bbsDto.id + " 큐레이션")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.homeview_curation_layout,
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
}