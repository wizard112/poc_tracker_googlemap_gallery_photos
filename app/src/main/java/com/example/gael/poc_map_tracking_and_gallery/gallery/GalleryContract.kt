package com.example.gael.poc_map_tracking_and_gallery.gallery

import com.example.gael.poc_map_tracking_and_gallery.BasePresenter
import com.example.gael.poc_map_tracking_and_gallery.BaseView

/**
 * Created on 12.10.17.
 */
interface GalleryContract {

    interface View : BaseView<Presenter> {
        fun getImages()
    }
    interface Presenter : BasePresenter {
        fun getView() : View
        fun getImages()
    }
}