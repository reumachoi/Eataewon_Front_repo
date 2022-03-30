package com.example.eataewon.connect

import android.os.Parcel
import android.os.Parcelable

// 리사이클러 뷰 아이템 클래스
class MapSearchListDto(
    val name: String?,      // 장소명
    val road: String?,      // 도로명 주소
    var phone: String?,                  // 전화번호
    var place_url: String?,              // 장소 상세페이지 URL
    val x: Double,         // 경도(Longitude)
    val y: Double)          // 위도(Latitude)
    : Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
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
        parcel.writeString(phone)
        parcel.writeString(place_url)
        parcel.writeDouble(x)
        parcel.writeDouble(y)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "MapSearchListDto(name=$name, road=$road, phone=$phone, place_url=$place_url, x=$x, y=$y)"
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
