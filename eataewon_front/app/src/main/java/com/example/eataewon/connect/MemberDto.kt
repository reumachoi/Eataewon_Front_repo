package com.example.eataewon.connect

import android.os.Parcel
import android.os.Parcelable

class MemberDto(

    val name:String?,
    var id:String?,
    val pwd:String?,
    val email:String?,
    val nickName:String?,
    val profilPic:Int,
    val likepoint:Int,
    val profilMsg:String?): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

        parcel.writeString(name)
        parcel.writeString(id)
        parcel.writeString(pwd)
        parcel.writeString(email)
        parcel.writeString(nickName)
        parcel.writeInt(profilPic)
        parcel.writeInt(likepoint)
        parcel.writeString(profilMsg)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MemberDto> {
        override fun createFromParcel(parcel: Parcel): MemberDto {
            return MemberDto(parcel)
        }

        override fun newArray(size: Int): Array<MemberDto?> {
            return arrayOfNulls(size)
        }
    }

}