package com.example.gael.poc_map_tracking_and_gallery.map

import com.example.gael.poc_map_tracking_and_gallery.BasePresenter
import com.example.gael.poc_map_tracking_and_gallery.BaseView

/**
 * Created on 11.10.17.
 */
 interface MapContract {

    interface View : BaseView<Presenter> {
        fun checkPermissionsMap()
        fun checkPermissionsLocation()
    }

    interface Presenter : BasePresenter{
        fun checkPermissionsMap()
        fun checkPermissionsLocation()
        fun getView() : View
    }
}