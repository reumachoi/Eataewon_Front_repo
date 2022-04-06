package com.example.eataewon.connect

import android.os.Parcel
import android.os.Parcelable


class MemberDto(
    var id:String?,
    val name:String?,
    val pwd:String?,
    val email:String?,
    val nickname:String?,
    val profilpic:String?,
    val likepoint:Int,
    val profilmsg:String?,
    val del:Int
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(pwd)
        parcel.writeString(email)
        parcel.writeString(nickname)
        parcel.writeString(profilpic)
        parcel.writeInt(likepoint)
        parcel.writeString(profilmsg)
        parcel.writeInt(del)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "MemberDto(id=$id, name=$name, pwd=$pwd, email=$email, nickname=$nickname, profilpic=$profilpic, likepoint=$likepoint, profilmsg=$profilmsg, del=$del)"
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