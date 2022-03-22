package com.example.eattaewon.connect

import android.os.Parcel
import android.os.Parcelable

class BbsDto(var id:String?,var seq:Int, var title:String?,  var content:String?, var hashtag:String?, var wdate:String?,
             var shopname:String?, var address:String?, var latitude:Double, var longitude:Double, var readcnt:Int, var likecnt:Int):Parcelable {
    constructor(parcel: Parcel) : this(
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
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(seq)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(hashtag)
        parcel.writeString(wdate)
        parcel.writeString(shopname)
        parcel.writeString(address)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeInt(readcnt)
        parcel.writeInt(likecnt)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "BbsDto(id=$id, seq=$seq, title=$title, content=$content, hashtag=$hashtag, wdate=$wdate, shopname=$shopname, address=$address, latitude=$latitude, longitude=$longitude, readcnt=$readcnt, likecnt=$likecnt)"
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