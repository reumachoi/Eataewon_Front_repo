package com.example.eataewon

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eataewon.connect.BbsDto
import com.example.eataewon.connect.BbsService
import com.example.eataewon.connect.KakaoSearchDto
import com.example.eataewon.connect.MapSearchListDto
import com.example.eataewon.databinding.ActivityKakaoMapBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchKakaoMapActivity : AppCompatActivity() {

    companion object {
        const val BASE_URL = "https://dapi.kakao.com/"
        const val API_KEY = "KakaoAK 972c845305000d5dfa3b3581cd061aa2"  // REST API 키
    }

    val binding by lazy { ActivityKakaoMapBinding.inflate(layoutInflater) }
    private val listItems = arrayListOf<MapSearchListDto>()   // 리사이클러 뷰 아이템
    private val listAdapter = MapListAdapter(listItems)    // 리사이클러 뷰 어댑터
    private var keyword = ""        // 검색 키워드
    var imm: InputMethodManager? = null // EditText 키보드 내리기
    var pos: Int? = null
    var rememV : View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?;

        val editAddr = intent.getStringExtra("editAddr")
        binding.etSearchField.setText(editAddr)

        // 리사이클러 뷰
        binding.rvList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvList.adapter = listAdapter

        // 리스트 아이템 클릭 시 해당 위치로 이동
        listAdapter.setItemClickListener(object: MapListAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val mapPoint = MapPoint.mapPointWithGeoCoord(listItems[position].y, listItems[position].x)
                binding.mapView.setMapCenterPointAndZoomLevel(mapPoint, 3, true)

                if(rememV != null){
                    rememV!!.setBackgroundColor(Color.DKGRAY)
                }

                pos = position
                rememV = v

                v.setBackgroundColor(Color.LTGRAY)
                binding.btnNextPage.isEnabled = true
            }
        })

        // 검색 버튼
        binding.btnSearch.setOnClickListener {
            keyword = binding.etSearchField.text.toString()
            searchKeyword(keyword)
            imm?.hideSoftInputFromWindow(binding.etSearchField.getWindowToken(), 0);

        }

        // 이전 페이지 버튼
        binding.btnPrevPage.setOnClickListener {
            var i = Intent(this,WriteActivity::class.java)
            startActivity(i)
        }

        // 다음 페이지 버튼
        binding.btnNextPage.setOnClickListener {
            println("~~~~~~~~~~~~~~~~~"+listItems[pos!!].name)
            var i = Intent(this,WriteActivity::class.java)
            i.putExtra("shopData",listItems[pos!!])
            startActivity(i)
        }

    }

    // 키워드 검색 함수
    private fun searchKeyword(keyword: String) {
        val retrofit = Retrofit.Builder()   // Retrofit 구성
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(BbsService::class.java)   // 통신 인터페이스를 객체로 생성
        val call = api.getSearchKeyword(API_KEY, keyword)   // 검색 조건 입력

        // API 서버에 요청
        call.enqueue(object: Callback<KakaoSearchDto> {
            override fun onResponse(
                call: Call<KakaoSearchDto>,
                response: Response<KakaoSearchDto>
            ) {
                // 통신 성공
                addItemsAndMarkers(response.body())
            }

            override fun onFailure(call: Call<KakaoSearchDto>, t: Throwable) {
                // 통신 실패
                Log.w("MainActivity", "통신 실패: ${t.message}")
            }
        })
    }

    // 검색 결과 처리 함수
    private fun addItemsAndMarkers(searchResult: KakaoSearchDto?) {
        if (!searchResult?.documents.isNullOrEmpty()) {
            // 검색 결과 있음
            listItems.clear()                   // 리스트 초기화
            binding.mapView.removeAllPOIItems() // 지도의 마커 모두 제거
            for (document in searchResult!!.documents) {
                // 결과를 리사이클러 뷰에 추가
                val item = MapSearchListDto(document.place_name,
                    document.road_address_name,
                    document.phone,
                    document.place_url,
                    document.x.toDouble(),
                    document.y.toDouble())
                listItems.add(item)

                // 지도에 마커 추가
                val point = MapPOIItem()
                point.apply {
                    itemName = document.place_name
                    mapPoint = MapPoint.mapPointWithGeoCoord(document.y.toDouble(),
                        document.x.toDouble())
                    markerType = MapPOIItem.MarkerType.BluePin
                    selectedMarkerType = MapPOIItem.MarkerType.RedPin
                }
                binding.mapView.addPOIItem(point)
            }
            listAdapter.notifyDataSetChanged()  //새로고침,갱신



        } else {
            // 검색 결과 없음
            Toast.makeText(this, "검색 결과가 없습니다", Toast.LENGTH_LONG).show()
        }
    }

}