package com.example.eattaewon

import android.os.Bundle
import androidx.annotation.UiThread
import androidx.fragment.app.FragmentActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource

class MapFragmentActivity : FragmentActivity(), OnMapReadyCallback {

    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_fragment_activity)

        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient("1o2hhvg6b8")

        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)


        /*//위치 변경 이벤트
        naverMap.addOnLocationChangeListener { location ->
            Toast.makeText(this, "${location.latitude}, ${location.longitude}",
                Toast.LENGTH_SHORT).show()
        }*/
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource

        naverMap.mapType = NaverMap.MapType.Basic
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true)
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        val cameraPosition = naverMap.cameraPosition    //현재 카메라 위치

        val options = NaverMapOptions()
            .camera(cameraPosition)
            .mapType(NaverMap.MapType.Basic)
        val mapFragment = MapFragment.newInstance(options)

    }



    fun setLocation(latitude:Double, longitude:Double){
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(latitude, longitude))
        naverMap?.moveCamera(cameraUpdate)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }



    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}
