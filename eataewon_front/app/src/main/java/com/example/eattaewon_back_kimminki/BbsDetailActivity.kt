package com.example.eattaewon_back_kimminki

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.eattaewon_back_kimminki.databinding.ActivityBbsDetailBinding

class BbsDetailActivity : AppCompatActivity() {

    val binding by lazy { ActivityBbsDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val retrofit = RetrofitClient.getInstance()
        val service: RetrofitBbs? = retrofit?.create(RetrofitBbs::class.java)   // 레트로핏 인터페이스 객체 구현

        /*val call = service?.test()
        val resp = call?.execute()
        binding.bbsList.text = resp?.body()*/

       /* val call = service?.getBbsList()
        val resp = call?.execute()
        val bbslist = resp?.body()*/
        //binding.bbsList.text = bbslist?.get(0)?.title

        binding.seeMapBtn.setOnClickListener {

            //map
            val fm = supportFragmentManager
            val fragmentTransaction = fm.beginTransaction()
            val mapsFragment = MapsFragment(this)
            fragmentTransaction.add(R.id.mapContent, mapsFragment) // <- 수정됨
            fragmentTransaction.commit()

            val geocoder: Geocoder = Geocoder(this)

//            var list: List<Address>? = geocoder.getFromLocationName(
//                bbslist?.get(0)?.address,  // 지역 이름
//                "서울시청",
//                10
//            )
            mapsFragment.setSeeBbsStoreLocation(37.33, 126.58)
        }

        binding.shareBtn.setOnClickListener {

            //기본 공유방법
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            val sendMessage = binding.bbsList.text
            intent.putExtra(Intent.EXTRA_TEXT, sendMessage)
            val shareIntent = Intent.createChooser(intent, "share")
            startActivity(shareIntent)

            /* // 피드 메시지 보내기

            // 카카오톡 설치여부 확인
            if (LinkClient.instance.isKakaoLinkAvailable(context)) {
                // 카카오톡으로 카카오링크 공유 가능
                LinkClient.instance.defaultTemplate(context, defaultFeed) { linkResult, error ->
                    if (error != null) {
                        Log.e(TAG, "카카오링크 보내기 실패", error)
                    }
                    else if (linkResult != null) {
                        Log.d(TAG, "카카오링크 보내기 성공 ${linkResult.intent}")
                        startActivity(linkResult.intent)

                        // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                        Log.w(TAG, "Warning Msg: ${linkResult.warningMsg}")
                        Log.w(TAG, "Argument Msg: ${linkResult.argumentMsg}")
                    }
                }
            } else {
                // 카카오톡 미설치: 웹 공유 사용 권장
                // 웹 공유 예시 코드
                val sharerUrl = WebSharerClient.instance.defaultTemplateUri(defaultFeed)

                // CustomTabs으로 웹 브라우저 열기

                // 1. CustomTabs으로 Chrome 브라우저 열기
                try {
                    KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
                } catch(e: UnsupportedOperationException) {
                    // Chrome 브라우저가 없을 때 예외처리
                }

                // 2. CustomTabs으로 디바이스 기본 브라우저 열기
                try {
                    KakaoCustomTabsClient.open(context, sharerUrl)
                } catch (e: ActivityNotFoundException) {
                    // 인터넷 브라우저가 없을 때 예외처리
                }
            }
        }*/


            /*val defaultLocation = LocationTemplate(
                address = "경기 성남시 분당구 판교역로 235 에이치스퀘어 N동 8층",
                addressTitle = "카카오 판교오피스 카페톡",
                content = Content(
                    title = "신메뉴 출시❤️ 체리블라썸라떼",
                    description = "이번 주는 체리블라썸라떼 1+1",
                    imageUrl = "https://mud-kage.kakao.com/dn/bSbH9w/btqgegaEDfW/vD9KKV0hEintg6bZT4v4WK/kakaolink40_original.png",
                    link = Link(
                        webUrl = "https://developers.com",
                        mobileWebUrl = "https://developers.kakao.com"
                    )
                ),
                social = Social(
                    likeCount = 286,
                    commentCount = 45,
                    sharedCount = 845
                )
            )*/

        }
    }
}