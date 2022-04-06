package com.example.eataewon


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker


class  NaverMapFragment : Fragment(), OnMapReadyCallback {

    //지도 객체 변수
    private lateinit var mapView: MapView
    private var nMap: NaverMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(
            R.layout.fragment_naver_map,
            container, false
        ) as ViewGroup
        mapView = rootView.findViewById<View>(R.id.navermap) as MapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        return rootView
    }

    override fun onMapReady(naverMap: NaverMap) {
        nMap = naverMap
        val options = NaverMapOptions()
            .camera(CameraPosition(LatLng(37.566, 126.978),  10.0))  // 카메라 위치 (위도,경도,줌)
            .mapType(NaverMap.MapType.Basic)    //지도 유형
            .enabledLayerGroups(NaverMap.LAYER_GROUP_BUILDING)  //빌딩 표시

        MapFragment.newInstance(options)

        val marker = Marker()
        marker.position = LatLng(37.566, 126.978)
        marker.map = naverMap
    }

    fun setLocation(lati: Double, longi: Double) {
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(lati, longi))
        nMap?.moveCamera(cameraUpdate)
        val marker = Marker()
        marker.position = LatLng(lati, longi)
        marker.map = nMap
        println("네이버 맵 마커,카메라 이동")
    }

        override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}