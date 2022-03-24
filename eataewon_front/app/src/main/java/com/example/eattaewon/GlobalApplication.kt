package com.example.eattaewon

import android.app.Application
import com.kakao.sdk.common.KakaoSdk


//카카오 로그인 파일
class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "a05f68a3850359a6d8c92e79cdc0d8cb")
    }
}