package com.example.gael.poc_map_tracking_and_gallery.Interfaces

import com.google.android.gms.maps.model.LatLng

/**
 * Created on 14.10.17.
 */

interface IPoliline {

    fun sendPoints(list : ArrayList<LatLng>)
}