package com.example.eataewon.connect

import android.os.Parcel
import android.os.Parcelable

class ScrapDto (
    val id: String?,
    val bbsseq : Int?,
    val seq : Int?
    ):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeValue(bbsseq)
        parcel.writeValue(seq)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScrapDto> {
        override fun createFromParcel(parcel: Parcel): ScrapDto {
            return ScrapDto(parcel)
        }

        override fun newArray(size: Int): Array<ScrapDto?> {
            return arrayOfNulls(size)
        }
    }

}