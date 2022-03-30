package com.example.eataewon

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.naver.maps.map.NaverMapSdk

class GlobalApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        //카카오 로그인 초기화
        KakaoSdk.init(this, "a05f68a3850359a6d8c92e79cdc0d8cb") 

        // Kakao sns SDK 초기화
        KakaoSdk.init(this, "803510af840ec6fd9c1aefe41148b4e5")

        // NaverMap SDK
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient("1o2hhvg6b8")
    }

}