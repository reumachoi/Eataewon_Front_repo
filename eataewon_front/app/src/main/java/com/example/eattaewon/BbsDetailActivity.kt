package com.example.eattaewon

import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.eattaewon.connect.BbsDao
import com.example.eattaewon.databinding.ActivityBbsDetailBinding
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
        //var result = BbsDao.getInstance().getBbsDetail(seq)

        binding.seeMapBtn.setOnClickListener {

          val naverMapFragment = NaverMapFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.mapContent, naverMapFragment).commit()

        }

        binding.shareBtn.setOnClickListener {

            /*//기본 공유방법
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            val sendMessage = binding.bbsList.text
            intent.putExtra(Intent.EXTRA_TEXT, sendMessage)
            val shareIntent = Intent.createChooser(intent, "share")
            startActivity(shareIntent)*/


        // 피드 메시지 보내기
            val defaultText = TextTemplate(
                text = """
        카카오링크는 카카오 플랫폼 서비스의 대표 기능으로써 사용자의 모바일 기기에 설치된 카카오 플랫폼과 연동하여 다양한 기능을 실행할 수 있습니다.
        현재 이용할 수 있는 카카오링크는 다음과 같습니다.
        카카오톡링크
        카카오톡을 실행하여 사용자가 선택한 채팅방으로 메시지를 전송합니다.
        카카오스토리링크
        카카오스토리 글쓰기 화면으로 연결합니다.
    """.trimIndent(),
                link = Link(
                    webUrl = "https://developers.kakao.com",
                    mobileWebUrl = "https://developers.kakao.com"
                )
            )
        // 사용자 정의 메시지 ID
        //  * 만들기 가이드: https://developers.kakao.com/docs/latest/ko/message/message-template
        //val templateId = templateIds["ID 73529"] as Long
        val url = "https://developers.kakao.com"

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
