package com.example.eataewon

import android.Manifest
import android.app.Activity
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.CameraPosition

class MapsFragment(val activity: Activity) : Fragment(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null

    lateinit var locationPermission: ActivityResultLauncher<Array<String>>
    // GPS를 사용해서 위치를 확인
    lateinit var fusedLocationClient: FusedLocationProviderClient
    // 위치값 요청에 대한 갱신 정보를 받는 변수
    lateinit var locationCallback: LocationCallback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        locationPermission = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { results ->
            if(results.all { it.value }) {
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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        //updateLocation()
    }
/*
    @SuppressLint("MissingPermission")
    fun updateLocation() {
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 1000
        }

        locationCallback = object: LocationCallback() {
            // 1초에 한번씩 변경된 위치 정보가 onLocationResult로 전달된다
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.let {
                    for ((i, location) in it.locations.withIndex()){
                        Log.d("Location", "위도: $i ${location.latitude}), 경도: 경도: ${location.longitude}")
                        setLastLocation(location)
                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }*/

    fun setLastLocation(lastLocation: Location) {
        val LATLNG = LatLng(lastLocation.latitude, lastLocation.longitude)

        // Google Map Marker Code
/*        var bitmapDrawable: BitmapDrawable
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bitmapDrawable = getDrawable(R.drawable.marker) as BitmapDrawable
        } else {
            bitmapDrawable = resources.getDrawable(R.drawable.marker) as BitmapDrawable
        }

        var discriptor = BitmapDescriptorFactory.fromBitmap(bitmapDrawable.bitmap)*/

        val markerOptions = MarkerOptions()
            .position(LATLNG)
            .title("Here!")
            //.icon(discriptor)

        val cameraPosition = CameraPosition.Builder()
            .target(LATLNG)
            .zoom(15.0f)
            .build()
        mMap?.clear()
        mMap?.addMarker(markerOptions)
        mMap?.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        // Google Map Marker Code

        //var markerImg = BitmapFactory.decodeFile("drawable/marker.png")
        //var scaledBitmap = Bitmap.createScaledBitmap(markerImg, 50, 50, false)


    }

    //글 상세보기에서 가게 위치 확인
    fun setSeeBbsStoreLocation(latitude:Double, longitude:Double){
        val LATLNG = LatLng(latitude, longitude)

        val markerOptions = MarkerOptions()
            .position(LATLNG)
            .title("Here!")

        val cameraPosition = CameraPosition.Builder()
            .target(LATLNG)
            .zoom(15.0f)
            .build()

        mMap?.clear()
        mMap?.addMarker(markerOptions)
        mMap?.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}