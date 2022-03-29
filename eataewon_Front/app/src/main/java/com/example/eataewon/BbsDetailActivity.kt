package com.example.eataewon


import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.eataewon.connect.BbsDto
import com.example.eataewon.databinding.ActivityBbsDetailBinding
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.link.WebSharerClient
import com.kakao.sdk.template.model.*

class BbsDetailActivity : AppCompatActivity() {

    val binding by lazy { ActivityBbsDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var seq = 1

        //var data = BbsDao.getInstance().getBbsDetail(seq)

        //어댑터에서 싼 짐 푸르기 (메인에서 디테일로 넘어온 데이터)
        val data = intent.getParcelableExtra<BbsDto>("data")

        binding.DeTitle.text = data?.title
        binding.DeContent.text = data?.content
        binding.DeHashtag.text = data?.hashtag
        binding.DeLocation.text = data?.address

        //이미 좋아요를 눌렀던 글인지 확인하는 조건문 필요 (좋아요 눌러놨으면 하트빨간색으로 표시해주기)
        //이미 스크랩을 눌렀던 글인지 확인하는 조건문 필요 (스크랩 눌러놨으면 노란리본으로 표시해주기)

//        var plusScrap = BbsDao.getInstance().plusBbsScrap(data!!)

        //var testData = listOf<BbsDto>("id",10,"title","content","picture")

//      좋아요 버튼 클릭효과
        binding.HeartBtn.setOnClickListener {
            if(binding.HeartBtn.isSelected != true){
                binding.HeartBtn.isSelected = true  //좋아요 누르기
                //+이태원라이크 테이블에 유저값 넣어주기
                // var plusLike = BbsDao.getInstance().plusBbsLike(data!!)
            }else{
                binding.HeartBtn.isSelected = false //좋아요 누른거 취소
                //+이태원라이크 테이블에 유저값 삭제하기
            }

        }
//      스크랩 버튼 클릭효과
        binding.ScrapBtn.setOnClickListener {
            if(binding.ScrapBtn.isSelected != true){
                binding.ScrapBtn.isSelected = true  //스크랩 누르기
                //+이태원스크랩 테이블에 유저값 넣어주기
                //  var plusScrap = BbsDao.getInstance().plusBbsScrap()   //스크랩누르면
            }else{
                binding.ScrapBtn.isSelected = false //스크랩 누른거 취소
                //+이태원스크랩 테이블에 유저값 삭제하기
            }
        }

        binding.seeMapBtn.setOnClickListener {


        }


//      카톡 글 공유하기
        binding.floShareBtn.setOnClickListener {

            // 피드 메시지 보내기
            val defaultText = LocationTemplate(
                address = data?.address!!,
                addressTitle = data?.shopname!!,
                content = Content(
                    title = data?.title!!,
                    description = data?.content!!.substring(0,20),
                    imageUrl = "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fmyplace-phinf.pstatic.net%2F20220301_56%2F1646125809470g3mRx_JPEG%2Fupload_8734af74cb52984491186a224aa2a66e.jpeg",
                    link = Link(
//                        webUrl = "https://developers.com",
                        mobileWebUrl = "https://developers.kakao.com"
                    )
                ),
                social = Social(
                    likeCount = 286,
                    commentCount = 45,
                    sharedCount = 845
                )
            )
            // 사용자 정의 메시지 ID
            //  * 만들기 가이드: https://developers.kakao.com/docs/latest/ko/message/message-template

            // 카카오톡 설치여부 확인
            if (LinkClient.instance.isKakaoLinkAvailable(this)) {
                // 카카오톡으로 카카오링크 공유 가능
                LinkClient.instance.defaultTemplate(this, defaultText) { linkResult, error ->
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
                val sharerUrl = WebSharerClient.instance.defaultTemplateUri(defaultText)

                // CustomTabs으로 웹 브라우저 열기

                // 1. CustomTabs으로 Chrome 브라우저 열기
                try {
                    KakaoCustomTabsClient.openWithDefault(this, sharerUrl)
                } catch(e: UnsupportedOperationException) {
                    // Chrome 브라우저가 없을 때 예외처리
                }

                // 2. CustomTabs으로 디바이스 기본 브라우저 열기
                try {
                    KakaoCustomTabsClient.open(this, sharerUrl)
                } catch (e: ActivityNotFoundException) {
                    // 인터넷 브라우저가 없을 때 예외처리
                }
            }

        }
    }
}