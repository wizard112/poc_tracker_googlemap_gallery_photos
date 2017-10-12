package com.example.gael.poc_map_tracking_and_gallery.Utils

import android.content.Context
import android.location.Geocoder
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import java.io.IOException

/**
 * Created on 12.10.17.
 * Utils for the map
 */

class MapUtils {

    companion object {

        //types for map
        val MAP_TYPES = arrayOf(GoogleMap.MAP_TYPE_SATELLITE,
                GoogleMap.MAP_TYPE_NORMAL,
                GoogleMap.MAP_TYPE_HYBRID,
                GoogleMap.MAP_TYPE_TERRAIN,
                GoogleMap.MAP_TYPE_NONE)

        /**
         * Allows to get address from position on map
         */
        fun getAddressFromLatLng(latLng : LatLng, ctxt : Context) : String {
            var address : String? = null
            if(latLng != null) {
                var geocoder : Geocoder  = Geocoder(ctxt)
                try {
                    address = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1).get(0).getAddressLine(0)
                }catch (ioe : IOException) {
                    ioe.printStackTrace()
                    Log.e("E",ioe.message)
                }
            }
            return address!!
        }
    }
}