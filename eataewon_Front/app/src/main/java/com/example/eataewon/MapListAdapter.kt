package com.example.eataewon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// 리사이클러 뷰 아이템 클래스
class ListLayout(val name: String,      // 장소명
                 val road: String,      // 도로명 주소
                 val address: String,   // 지번 주소
                 val x: Double,         // 경도(Longitude)
                 val y: Double)         // 위도(Latitude)


class MapListAdapter (val itemList: ArrayList<ListLayout>): RecyclerView.Adapter<MapListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.map_list_view_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MapListAdapter.ViewHolder, position: Int) {
        holder.name.text = itemList[position].name
        holder.road.text = itemList[position].road
        holder.address.text = itemList[position].address

        // 아이템 클릭 이벤트
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_list_name)
        val road = itemView.findViewById<TextView>(R.id.tv_list_road)
        val address = itemView.findViewById<TextView>(R.id.tv_list_address)
    }

    interface OnItemClickListener { //클릭 이벤트를 위한 인터페이스
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener
}