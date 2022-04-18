package com.example.eataewon.connect

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eataewon.R
import com.example.eataewon.connect.MapSearchListDto


class MapListAdapter(val itemList: ArrayList<MapSearchListDto>):
    RecyclerView.Adapter<MapListAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.map_list_view_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = itemList[position].name
        holder.road.text = itemList[position].road

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