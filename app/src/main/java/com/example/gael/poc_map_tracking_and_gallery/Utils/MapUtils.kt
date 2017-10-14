package com.example.gael.poc_map_tracking_and_gallery.Utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.example.gael.poc_map_tracking_and_gallery.Interfaces.IPoliline
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import com.google.android.gms.vision.barcode.Barcode.GeoPoint
import org.json.JSONException
import com.google.maps.android.PolyUtil
import org.json.JSONObject
import org.json.JSONArray





/**
 * Created on 12.10.17.
 * Utils for the map
 */

class MapUtils {

    companion object {

        val KEY_ROUTES : String = "routes"
        val KEY_OVERVIEW_POLYLINE : String = "overview_polyline"
        val KEY_POINTS : String = "points"

        //types for map
        val MAP_TYPES = arrayOf(GoogleMap.MAP_TYPE_SATELLITE,
                GoogleMap.MAP_TYPE_NORMAL,
                GoogleMap.MAP_TYPE_HYBRID,
                GoogleMap.MAP_TYPE_TERRAIN,
                GoogleMap.MAP_TYPE_NONE)

        var urlMapApiStatic : String = "http://maps.googleapis.com/maps/api/directions/json"

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

        /**
         * Allows to get position from address on map
         */
        fun getLatLngFromAddress(addres : String, ctxt : Context) : LatLng {
            var location : Address? = null

            if(addres != null && addres.isNotEmpty()) {
                var geocoder : Geocoder  = Geocoder(ctxt)
                var addresses : List<Address>

                try {
                    addresses = geocoder.getFromLocationName(addres,5)

                    location = addresses.get(0)
                    if(location.latitude.toString().length > 10) {
                        val newLatitude : String = location.latitude.toString().subSequence(0,9).toString()
                        location.latitude = newLatitude.toDouble()
                    }
                    //Log.i("Test",ttr)
                    //Log.i("Test",location.latitude.toString().plus(",").plus(location.longitude))

                }catch (ioe : IOException) {
                    ioe.printStackTrace()
                    Log.e("E",ioe.message)
                }
            }
            return LatLng(location!!.latitude,location!!.longitude)
        }

        /**
         * Get the poliline from origin and destination
         */
        fun getPointsInPoliline (origineLat : Double,originLng : Double,destLat : Double, destLng : Double, comm : IPoliline) {
            var completeUrl : String = urlMapApiStatic.plus("?origin=")
                    .plus(origineLat).plus(",").plus(originLng).plus("&destination=")
                    .plus(destLat).plus(",")
                    .plus(destLng).plus("&mode=driving")
            Utils.executeUrl(completeUrl,comm)
        }

        /**
         * INterpret the poliline
         */
        fun treatPoliline(result : String, comm : IPoliline){
            var strPoints = StringBuilder()
            var list : ArrayList<LatLng> = ArrayList()
            try {
                var mainObject : JSONObject = JSONObject(result)
                if (mainObject.has(KEY_ROUTES)) {
                    var routes = mainObject.getJSONArray(KEY_ROUTES)
                    val firstIndex = routes.get(0) as JSONObject
                    if (firstIndex.has(KEY_OVERVIEW_POLYLINE)) {
                        val overviewPolyline = firstIndex.get(KEY_OVERVIEW_POLYLINE) as JSONObject
                        val points = overviewPolyline.get(KEY_POINTS) as String
                        val pr = PolyUtil.decode(points)
                        for (p in pr) {
                            list.add(p)
                            val coord = p.latitude.toString() + "," + p.longitude
                            strPoints.append(coord)
                            strPoints.append("|")
                        }
                        strPoints.deleteCharAt((strPoints.length-1))
                        //Log.i("Test",strPoints.toString())
                        comm.sendPoints(list)
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                Log.e("E", "error json object " + e.message)
            }

        }
    }
}