package com.example.gael.poc_map_tracking_and_gallery.Utils

/**
 * Created on 16.10.17.
 */

class FacebookUtil {

    companion object {
        val IMG_URL : String = "https://graph.facebook.com/"
        val FIELDS_PROFILE : String = "id,name,email,gender,birthday"
        val FIELDS : String = "fields"

        fun getImgUrlProfile(userID : String) : String{
            return IMG_URL.plus(userID).plus("/picture?type=large")
        }
    }
}