package com.example.gael.poc_map_tracking_and_gallery.Utils

import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.util.Log

/**
 * Created on 12.10.17.
 */

class Utils {

    companion object {

        /**
         * Check if permission(s) is/are not granted
         * @param : arr represents a permissions array
         * @param : cxt represents the context
         *
         * @return a list of permissions fi are not granted, else empty if is granted
         */
        fun checkPermissions(arr : Array<String>, cxt : Context) : ArrayList<String>{
            var res : ArrayList<String> = ArrayList<String>()
            if(arr != null && arr.size > 0){
                arr.forEach { permission ->
                    if(ActivityCompat.checkSelfPermission(cxt,permission) != PackageManager.PERMISSION_GRANTED) {
                        res.add(permission)
                    }
                }
            }
            return res;
        }
    }
}