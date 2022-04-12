package com.example.eataewon.connect

import android.os.Parcel
import android.os.Parcelable

class BbsParam(
    var choice:String?,
    var search:String?,
    ):Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()
        ) {

        }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(choice)
        parcel.writeString(search)
    }

    companion object CREATOR : Parcelable.Creator<BbsParam> {
        override fun createFromParcel(parcel: Parcel): BbsParam {
            return BbsParam(parcel)
        }

        override fun newArray(size: Int): Array<BbsParam?> {
            return arrayOfNulls(size)
        }
    }


}