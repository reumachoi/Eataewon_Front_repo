package com.example.eataewon

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

class BbsAdapter (private val context: Context, private val dataList: ArrayList<BbsDto>) :
        RecyclerView.Adapter<BbsAdapter.ItemViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BbsAdapter.ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.search_view_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: BbsAdapter.ItemViewHolder, position: Int) {
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

        fun bind(bbsVO: BbsDto, context: Context) {

            /*if (bbsVO.picture != "") {
                val resourceId =
                    context.resources.getIdentifier(bbsVO.picture, "drawable", context.packageName)

                if (resourceId > 0) {
                    shopPhoto.setImageResource(resourceId)
                } else {
                    Log.d("", "들어옴")

                    val file: File = File(bbsVO.picture)
                    val bExist = file.exists()
                    if (bExist) {
                        Log.d("", "이미지 파일 있음")
                        val myBitmap = BitmapFactory.decodeFile(bbsVO.picture)
                        shopPhoto.setImageBitmap(myBitmap)
                    } else {
                        Log.d("", "${bbsVO.picture} 이미지 파일 없음")
                    }
                }
            } else {
                shopPhoto.setImageResource(R.mipmap.ic_launcher_round)
            }*/

            shopPhoto.setImageResource(bbsVO.picture)
            shopName.text = bbsVO.shopname
            address.text = bbsVO.address
            hashtag.text = bbsVO.hashtag

            // 게시물 클릭시 BbsDetailActivity로 이동
            itemView.setOnClickListener {
                Intent(context, BbsDetailActivity::class.java).apply {

                    // 짐싸!
                    putExtra("data", data)

                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }
        }
    }

}