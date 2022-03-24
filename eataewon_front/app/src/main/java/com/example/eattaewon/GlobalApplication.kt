package com.example.eattaewon

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.naver.maps.map.NaverMapSdk

class GlobalApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        // Kakao SDK 초기화
        KakaoSdk.init(this, "803510af840ec6fd9c1aefe41148b4e5")

        // NaverMap SDK
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient("1o2hhvg6b8")
    }

}