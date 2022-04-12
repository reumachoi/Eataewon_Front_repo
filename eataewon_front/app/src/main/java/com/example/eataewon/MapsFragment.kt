package com.example.eataewon

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.eataewon.connect.BbsDao
import com.example.eataewon.connect.BbsDto
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsFragment(val activity: Activity) : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    // 권한 처리가 두 개 이상일 경우 Array에 받음
    lateinit var locationPermission: ActivityResultLauncher<Array<String>>
    // GPS를 사용해서 위치를 확인
    lateinit var fusedLocationClient: FusedLocationProviderClient
    // 위치값 요청에 대한 갱신 정보를 받는 변수
    lateinit var locationCallback: LocationCallback

    private var markerShop : Marker? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        locationPermission = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { results ->
            if(results.all { it.value }) {
                startProcess()
            } else {
                Toast.makeText(activity, "권한 승인이 필요합니다", Toast.LENGTH_LONG).show()
            }
        }

        // 권한 요청
        locationPermission.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)
        )

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    private fun startProcess() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true     // 내 현재 위치 표기
        mMap.setOnMyLocationButtonClickListener(this)      // 버튼 클릭 시, 내 위치로 맵 이동
        mMap.setOnMyLocationClickListener(this)         // 내 위치 정보 표기

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        //updateLocation()
        

        val addressData = BbsDao.getInstance().getMarkerListApp() as ArrayList<BbsDto>

        for (shop in addressData) {
            val shopPosition = LatLng(shop.latitude, shop.longitude)
            val shopMarker = MarkerOptions().position(shopPosition).title(shop.shopname)
            mMap.addMarker(shopMarker)

            val latLngBounds = LatLngBounds.builder()
            latLngBounds.include(shopMarker.position)
            val bounds = latLngBounds.build()
            val padding = 0
            val update = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f))
            mMap.moveCamera(update)
        }

        markerShop?.tag = 0     // marker가 클릭되면 클릭수를 저장
        mMap.setOnMarkerClickListener(this)

    }
    override fun onMyLocationClick(location: Location) {
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }



    /*@SuppressLint("MissingPermission")
    fun updateLocation() {
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object: LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~onLocationResult")
                locationResult?.let {
                    for ((i, location) in it.locations.withIndex()){
                        Log.d("Location", "위도: $i ${location.latitude}), 경도: 경도: ${location.longitude}")
                        setLastLocation(location)
                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    fun setLastLocation(lastLocation: Location) {
        val LATLNG = LatLng(lastLocation.latitude, lastLocation.longitude)

        //val markerOptions = MarkerOptions().position(LATLNG).title("Here!")

        val cameraPosition = CameraPosition.Builder()
            .target(LATLNG)
            .zoom(15.0f)
            .build()
        mMap.clear()
        //mMap.addMarker(markerOptions)
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        // Google Map Marker Code
*//*        var bitmapDrawable: BitmapDrawable
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bitmapDrawable = getDrawable(R.drawable.marker) as BitmapDrawable
        } else {
            bitmapDrawable = resources.getDrawable(R.drawable.marker) as BitmapDrawable
        }

        var discriptor = BitmapDescriptorFactory.fromBitmap(bitmapDrawable.bitmap)*//*

        // Google Map Marker Code

        //var markerImg = BitmapFactory.decodeFile("drawable/marker.png")
        //var scaledBitmap = Bitmap.createScaledBitmap(markerImg, 50, 50, false)
    }*/

    override fun onMarkerClick(marker: Marker): Boolean {
        // Retrieve the data from the marker.
        val clickCount = marker.tag as? Int

        // Check if a click count was set, then display the click count.
        clickCount?.let {
            val newClickCount = it + 1
            marker.tag = newClickCount
            Toast.makeText(
                activity,
                "${marker.title} has been clicked $newClickCount times.",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false
    }


}