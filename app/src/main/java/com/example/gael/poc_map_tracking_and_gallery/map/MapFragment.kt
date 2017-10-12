package com.example.gael.poc_map_tracking_and_gallery.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gael.poc_map_tracking_and_gallery.MainActivity
import com.example.gael.poc_map_tracking_and_gallery.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_map.view.*
import com.google.maps.android.ui.IconGenerator
import java.text.DateFormat
import java.util.*


/**
 * Created on 11.10.17.
 */

class MapFragment : Fragment(), MapContract.View, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        LocationListener{

    lateinit var mPresenter : MapContract.Presenter
    lateinit var mMapView : MapView
    lateinit var myGoogleMap: GoogleMap
    val TAG = "MapFRagment"
    lateinit var mGoogleApiClient : GoogleApiClient
    //to make a quality request to FusedLOcationProviderApi
    lateinit var locationRequest : LocationRequest
    var valueZoom : Float = 12f

    var interval : Long = 0
    var fastInterval : Long = 0
    var myCurrentLocation: Location? = null
    var myLastUpdate : String = ""

    companion object {
        fun newInstance() : MapFragment { return MapFragment()
        }
    }

    override fun setPresenter(presenter: MapContract.Presenter) {
        mPresenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fastInterval = 1000 * 15
        interval = 1000 * 30
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

        createLocationRequest()
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


    /**
     * initilize the listenrs
     */
    private fun initListeners() {
        myGoogleMap.setOnMarkerClickListener(this)
        myGoogleMap.setOnMapLongClickListener(this)
        myGoogleMap.setOnInfoWindowClickListener(this)
        myGoogleMap.setOnMapClickListener(this)
    }

    /**
     * Create a request of quality
     * set interval for location updates in milisecondes
     * set fastest interval
     * set the priority of the request
     */
    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.setInterval(interval)
        locationRequest.fastestInterval = fastInterval
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    }

    /**
     * Check if the user has activated the permissiosn for the location
     * initialize the map with marker, zoom and options/settings
     */
    override fun checkPermissionsMap() {
        if(ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), MainActivity.REQUEST_LOCATION_MAP)
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
            val cameraPosition = CameraPosition.Builder().target(bxl).zoom(valueZoom).build()
            myGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    override fun checkPermissionsLocation() {
        if(ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), MainActivity.REQUEST_LOCATION_LOCATION)
        } else {
            var pendingResult : PendingResult<Status> = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,locationRequest,this)
        }
    }

    /**
     * call the map view on resume
     * if the applications was in pause, we re launch the location update
     */
    override fun onResume() {
        mMapView.onResume()
        super.onResume()
        if(mGoogleApiClient.isConnected){
            startLocationUpdates()
        }
    }

    override fun onDestroy() {
        mMapView.onDestroy()
        super.onDestroy()
    }

    /**
     * Connect the google api client
     */
    override fun onStart() {
        super.onStart()
        mGoogleApiClient.connect()
    }

    /**
     * disconnect the google api client
     */
    override fun onStop() {
        super.onStop()
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected ) {
            mGoogleApiClient.disconnect()
        }
    }

    /**
     * Stop the location updates
     */
    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    /**
     * @PendingResult is a pending result from API in Google Play services. The result is retrieved via a callback in my Fragment
     */
    private fun startLocationUpdates () {
        //var pendingResult : PendingResult<Status> = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,locationRequest,this)
        checkPermissionsLocation()
    }

    private fun addMarker() {
        valueZoom = 13f
        var options : MarkerOptions = MarkerOptions()

        // following four lines requires 'Google Maps Android API Utility Library'
        // https://developers.google.com/maps/documentation/android/utility/
        // I have used this to display the time as title for location markers
        // you can safely comment the following four lines but for this info
        var iconFactory : IconGenerator = IconGenerator(activity)
        iconFactory.setStyle(IconGenerator.STYLE_PURPLE);
        options.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(myLastUpdate)))
        options.anchor(iconFactory.anchorU, iconFactory.anchorV)

        var currentLatLng : LatLng = LatLng(myCurrentLocation!!.latitude, myCurrentLocation!!.longitude)
        options.position(currentLatLng)
        var mapMarker : Marker = myGoogleMap.addMarker(options)
        var atTime : Long = myCurrentLocation!!.time
        myLastUpdate = DateFormat.getTimeInstance().format(Date(atTime))
        mapMarker.setTitle(myLastUpdate)
        myGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, valueZoom))
    }

    private fun stopLocationUpdates() {
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected){
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
        }
    }

    override fun onLocationChanged(currentLocation: Location?) {
        myCurrentLocation = currentLocation
        myLastUpdate = DateFormat.getTimeInstance().format(Date())
        addMarker()
    }

    /**
     * start the location updates
     */
    override fun onConnected(p0: Bundle?) {
        startLocationUpdates()
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onInfoWindowClick(p0: Marker?) {

    }

    override fun onMapLongClick(p0: LatLng?) {

    }

    //add a marker
    override fun onMapClick(latLng: LatLng?) {
    }

    //show marker window
    override fun onMarkerClick(marker: Marker?): Boolean {
        //val resp = MapUtils.getAddressFromLatLng(marker!!.position, activity)
        marker!!.showInfoWindow()
        return true
    }
}