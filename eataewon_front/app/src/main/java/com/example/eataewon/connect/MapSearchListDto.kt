package com.example.eataewon.connect

import android.os.Parcel
import android.os.Parcelable

// 리사이클러 뷰 아이템 클래스
class MapSearchListDto(
    val name: String?,      // 장소명
    val road: String?,      // 도로명 주소
    val address: String?,   // 지번 주소
    val x: Double,         // 경도(Longitude)
    val y: Double)          // 위도(Latitude)
    : Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(road)
        parcel.writeString(address)
        parcel.writeDouble(x)
        parcel.writeDouble(y)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MapSearchListDto> {
        override fun createFromParcel(parcel: Parcel): MapSearchListDto {
            return MapSearchListDto(parcel)
        }

        override fun newArray(size: Int): Array<MapSearchListDto?> {
            return arrayOfNulls(size)
        }
    }
}
