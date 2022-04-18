package com.example.eataewon.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.*
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eataewon.BbsDetailActivity
import com.example.eataewon.R
import com.example.eataewon.connect.BbsDao
import com.example.eataewon.connect.BbsDto

class SearchBbsAdapter (private val context: Context, private val dataList: ArrayList<BbsDto>) :
        RecyclerView.Adapter<SearchBbsAdapter.ItemViewHolder>(), Filterable
{

    var filteredPersons = ArrayList<BbsDto>()
    var itemFilter = ItemFilter()

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

            //val dto = BbsDto("", "", null, "", "", 0, "", "", "", "", "", "", 0.0, 0.0, 0, 0, "")

            // 매장 사진을 공백 간격일 때마다 잘라서 배열에 저장
            val picArray = bbsDto.picture!!.split(" ")

            shopPhoto.setImageURI(Uri.parse(picArray[0]))
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

    override fun getFilter(): Filter {
        return itemFilter
    }

    inner class ItemFilter : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterString = charSequence.toString()
            val results = FilterResults()


            //검색이 필요없을 경우를 위해 원본 배열을 복제
            val filteredList: ArrayList<BbsDto> = ArrayList<BbsDto>()
            //공백제외 아무런 값이 없을 경우 -> 원본 배열
            if (filterString.trim { it <= ' ' }.isEmpty()) {
                results.values = dataList
                results.count = dataList.size

                return results
                //공백제외 2글자 이인 경우 -> 이름으로만 검색
            } else if (filterString.trim { it <= ' ' }.length <= 2) {
                for (shop in dataList) {
                    if (shop.shopname!!.contains(filterString)) {
                        filteredList.add(shop)
                    }
                }
                //그 외의 경우(공백제외 2글자 초과) -> 이름/전화번호로 검색
            } else {
                for (shop in dataList) {
                    if (shop.shopname!!.contains(filterString) || shop.address!!.contains(filterString)) {
                        filteredList.add(shop)
                    }
                }
            }
            results.values = filteredList
            results.count = filteredList.size

            return results
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
            filteredPersons.clear()
            filteredPersons.addAll(filterResults.values as ArrayList<BbsDto>)
            notifyDataSetChanged()
        }
    }
}

