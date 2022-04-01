package com.example.eataewon.connect

import android.os.Parcel
import android.os.Parcelable

//member+bbs 테이블 합친 dto
class MemberBbsDto ( var id:String?,
                     var seq:Int,
                     var title:String?,
                     var content:String?,
                     var picture:Int,
                     var hashtag:String?,
                     var wdate:String?,
                     var shopname:String?,
                     var address:String?,
                     var latitude:Double,
                     var longitude:Double,
                     var readcnt:Int,
                     var likecnt:Int,

                     val name:String?,
                     val pwd:String?,
                     val email:String?,
                     val nickName:String?,
                     val profilPic:Int,
                     val likepoint:Int,
                     val profilMsg:String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
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
        parcel.writeString(id)
        parcel.writeInt(seq)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeInt(picture)
        parcel.writeString(hashtag)
        parcel.writeString(wdate)
        parcel.writeString(shopname)
        parcel.writeString(address)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeInt(readcnt)
        parcel.writeInt(likecnt)
        parcel.writeString(name)
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

    override fun toString(): String {
        return "MemberBbsDto(id=$id, seq=$seq, title=$title, content=$content, picture=$picture, hashtag=$hashtag, wdate=$wdate, shopname=$shopname, address=$address, latitude=$latitude, longitude=$longitude, readcnt=$readcnt, likecnt=$likecnt, name=$name, pwd=$pwd, email=$email, nickName=$nickName, profilPic=$profilPic, likepoint=$likepoint, profilMsg=$profilMsg)"
    }

    companion object CREATOR : Parcelable.Creator<MemberBbsDto> {
        override fun createFromParcel(parcel: Parcel): MemberBbsDto {
            return MemberBbsDto(parcel)
        }

        override fun newArray(size: Int): Array<MemberBbsDto?> {
            return arrayOfNulls(size)
        }
    }

}