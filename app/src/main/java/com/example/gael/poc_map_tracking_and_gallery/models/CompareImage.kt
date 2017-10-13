package com.example.gael.poc_map_tracking_and_gallery.models

/**
 * Created by gael on 13.10.17.
 */

class CompareImage {
    companion object  : Comparator<Image>{
        override fun compare(o1: Image?, o2: Image?): Int {
            var value : Int = 1
            when {
                o1!!.idIMage!! > o2!!.idIMage!! -> value = 1
                o1!!.idIMage!! == o2!!.idIMage!! -> value = 0
                o1!!.idIMage!! < o2!!.idIMage!! -> value = -1
            }
            return value
        }

    }
}