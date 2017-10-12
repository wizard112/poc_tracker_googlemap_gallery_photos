package com.example.gael.poc_map_tracking_and_gallery.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gael.poc_map_tracking_and_gallery.MainActivity
import com.example.gael.poc_map_tracking_and_gallery.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_map.view.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MarkerOptions





/**
 * Created on 11.10.17.
 */

class MapFragment : Fragment(), MapContract.View, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener {

    lateinit var mPresenter : MapContract.Presenter
    lateinit var mMapView : MapView
    lateinit var myGoogleMap: GoogleMap
    val REQUEST_LOCATION = 896
    lateinit var mGoogleApiClient : GoogleApiClient

    companion object {
        fun newIntent() : MapFragment { return MapFragment()
        }
    }

    override fun setPresenter(presenter: MapContract.Presenter) {
        mPresenter = presenter
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v : View =  inflater!!.inflate(R.layout.fragment_map,container,false)
        mMapView = v.mapView

        mMapView!!.onCreate(savedInstanceState)

        MapsInitializer.initialize(activity)

        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mGoogleApiClient = GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

        //map is ready
        mMapView.getMapAsync { googleMap ->
            myGoogleMap = googleMap

            checkPermissionsMap()
        }
    }


    private fun initListeners() {
        myGoogleMap.setOnMarkerClickListener(this)
        myGoogleMap.setOnMapLongClickListener(this)
        myGoogleMap.setOnInfoWindowClickListener(this)
        myGoogleMap.setOnMapClickListener(this)
    }

    override fun checkPermissionsMap() {
        if(ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), MainActivity.REQUEST_LOCATION)
        } else {
            initListeners()

            myGoogleMap.setMyLocationEnabled(true)
            myGoogleMap.uiSettings.setMyLocationButtonEnabled(true)
            myGoogleMap.isTrafficEnabled = true
            myGoogleMap.uiSettings.isZoomControlsEnabled = true

            // For dropping a marker at a point on the Map
            val bxl = LatLng(50.8537638, 4.3147165)
            myGoogleMap.addMarker(MarkerOptions().position(bxl).title("Brussels").snippet("Brussels ma belle :-) "))


            // For zooming automatically to the location of the marker
            val cameraPosition = CameraPosition.Builder().target(bxl).zoom(12f).build()
            myGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    override fun onResume() {
        mMapView.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mMapView.onDestroy()
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        mGoogleApiClient.connect()
    }

    override fun onConnected(p0: Bundle?) {
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onInfoWindowClick(p0: Marker?) {

    }

    override fun onMapLongClick(p0: LatLng?) {

    }

    override fun onMapClick(p0: LatLng?) {

    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        return true
    }
}