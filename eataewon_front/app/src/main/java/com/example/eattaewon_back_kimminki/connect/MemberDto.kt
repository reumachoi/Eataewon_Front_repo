package com.example.eattaewon_back_kimminki.connect

import android.os.Parcel
import android.os.Parcelable

class MemberDto(
    var id:String?,
    val pwd:String?,
    val name:String?,
    val email:String?,
    val nickname:String?,
    val profilpic:String?,
    val likepoint:Int )