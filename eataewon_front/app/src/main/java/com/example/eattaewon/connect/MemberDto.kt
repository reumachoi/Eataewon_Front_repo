package com.example.eattaewon.connect

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
    val profilMsg:String?): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(seq)
        parcel.writeString(name)
        parcel.writeString(id)
        parcel.writeString(pwd)
        parcel.writeString(email)
        parcel.writeString(nickname)
        parcel.writeString(profilPic)
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