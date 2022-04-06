package com.example.eataewon

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.naver.maps.map.NaverMapSdk

//카카오 로그인
class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "a05f68a3850359a6d8c92e79cdc0d8cb")

        // NaverMap SDK
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient("1o2hhvg6b8")
    }

}