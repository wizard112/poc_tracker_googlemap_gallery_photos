package com.example.gael.poc_map_tracking_and_gallery.Interfaces

import com.example.gael.poc_map_tracking_and_gallery.models.Image

/**
 * Created on 13.10.17.
 */

interface IBottomNavigationActivity {
    fun replaceFragment(list : ArrayList<Image>, selectedImage : Int)
}