package com.example.eattaewon_back_kimminki.connect

import android.os.Parcel
import android.os.Parcelable

class MemberDto(
    var seq:Int,
    val name:String?,
    var id:String?,
    val pwd:String?,
    val email:String?,
    val nickname:String?,
    val profilPic:String?,
    val likepoint:Int,
    val profilMsg:String?)