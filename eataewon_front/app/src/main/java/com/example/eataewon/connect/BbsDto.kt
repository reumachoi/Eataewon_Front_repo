package com.example.eataewon.connect

import android.os.Parcel
import android.os.Parcelable


class BbsDto(
    var id:String?,
    var nickname:String?,
    var seq: Int?,
    var title:String?,
    var content:String?,
    var picture:Int,
    var hashtag:String?,
    var wdate:String?,
    var shopname:String?,
    var address:String?,
    var shopphnum:String?,
    var shopurl:String?,
    var latitude:Double,
    var longitude:Double,
    var readcnt:Int,
    var likecnt:Int,
    var testurl:String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nickname)
        parcel.writeValue(seq)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeInt(picture)
        parcel.writeString(hashtag)
        parcel.writeString(wdate)
        parcel.writeString(shopname)
        parcel.writeString(address)
        parcel.writeString(shopphnum)
        parcel.writeString(shopurl)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeInt(readcnt)
        parcel.writeInt(likecnt)
        parcel.writeString(testurl)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "BbsDto(id=$id, nickname=$nickname, seq=$seq, title=$title, content=$content, picture=$picture, hashtag=$hashtag, wdate=$wdate, shopname=$shopname, address=$address, shopphnum=$shopphnum, shopurl=$shopurl, latitude=$latitude, longitude=$longitude, readcnt=$readcnt, likecnt=$likecnt, testurl=$testurl)"

    }

    companion object CREATOR : Parcelable.Creator<BbsDto> {
        override fun createFromParcel(parcel: Parcel): BbsDto {
            return BbsDto(parcel)
        }

        override fun newArray(size: Int): Array<BbsDto?> {
            return arrayOfNulls(size)
        }
    }
}


